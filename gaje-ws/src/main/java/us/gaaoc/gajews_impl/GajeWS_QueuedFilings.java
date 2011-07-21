package us.gaaoc.gajews_impl;

import javax.persistence.*;
import javax.xml.namespace.QName;

import us.gaaoc.gajews.QueuedFilings;
import us.gaaoc.gajews.types.*;


import us.gaaoc.framework.model.CaseRecord;
import us.gaaoc.framework.model.CaseRecordEventType;
import us.gaaoc.framework.model.CaseRecordHistory;
import us.gaaoc.framework.model.CaseRecordStatus;
import us.gaaoc.framework.model.DocumentBinaries;
import us.gaaoc.framework.model.Documents;
import us.gaaoc.framework.model.constants.GajeConstants;
//import us.gaje.service.CaseRecordService;
import us.gaje.model.service.util.ServiceFactory;
import us.gaje.service.CaseFilingValidator;
import us.gaje.service.CourtAccessDefinitions;
import us.gaje.service.pdf.CVNumberStampController;
import us.gaje.service.pdf.CourtDateStampController;
import us.gaje.service.pdf.CourtStampController;
import us.gaje.service.pdf.exceptions.StampingException;
import us.gaje.service.web.SessionService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;

import org.apache.cxf.binding.soap.SoapFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is the implementation of the GAJE QueuedFilings Webservice 
 * @author artripa
 */
@Transactional
@javax.jws.WebService(
        serviceName = "GAJE_ImportWSService",
        portName = "GAJE_ImportWS",
        targetNamespace = "http://GAJEWS.gaaoc.us/",
        wsdlLocation = "classpath:GAJE_ImportWS.wsdl",
        endpointInterface = "us.gaaoc.gajews.QueuedFilings")
public class GajeWS_QueuedFilings implements QueuedFilings
{
	
	
	
	//TODO: moved to a named query
	private String getCaseRecordsByStatus = 
		"select cr.uuid from CaseRecord cr where cr.status.code = :statusCode and cr.court.id = :courtID and cr.deletedFilingFlag = 0 and cr.clearFilingFlag = 0";

	private String getCaseRecordByUuid = 
		"from CaseRecord cr where cr.uuid = :caseRecord";
	
	@PersistenceContext
	private EntityManager entityManager;
	private Logger log = Logger.getLogger(GajeWS_QueuedFilings.class);
	private Logger methodLog = Logger.getLogger("methodmailing");
	private Logger faultLog = Logger.getLogger("wsFaultLog");
	private Logger rejectLog = Logger.getLogger("rejectlog");
	
	private SessionService sessionService;
	private CourtAccessDefinitions courtAccessDefinitions;
	private DomToWebserviceConverter converter;
	private ServiceFactory serviceFactory;
	private CourtStampController courtStampController;
	private CVNumberStampController cvNumberStampController;
	private CourtDateStampController courtDateStampController;
	private CaseFilingValidator caseFilingValidator;	
	//private CaseRecordService caseRecordService;
	
	private String level;
	
	
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public SessionService getSessionService() {
		return sessionService;
	}

	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

	public void setCaseFilingValidator(CaseFilingValidator caseFilingValidator) {
		this.caseFilingValidator = caseFilingValidator;
	}
	
	/*
	public void setCaseRecordService(CaseRecordService caseRecordService) {
		this.caseRecordService = caseRecordService;
	}
	*/
	
	public SoapFault exnDocumentNotFound(String id)
	{
		return new SoapFault(String.format("Document %s Not Found (user: %s)!",id,sessionService.getUsername()),
				new QName("document_not_found"));
	}
	
	public SoapFault exnError(String msg)
	{
		return new SoapFault(String.format("ERROR: User: %s %s",sessionService.getUsername(),msg),
				new QName("Server"));
	}
	
	public SoapFault exnAccessDenied(String court)
	{
		return new SoapFault(
					String.format("Authentication of user %s with court %s failure!",								
								sessionService.getUsername(),
								court),
				new QName("access_denied"));
	}
	
	public SoapFault exnInvalidCase(CaseRecord cr)
	{		
		return new SoapFault(
					String.format("Invalid case data! %s %s",								
								cr.getCaseCaption(),
								cr.getUuid()),
				new QName("invalid_case"));
	}


	@SuppressWarnings("unchecked")
	private List<String> getQueuedFilingsUuidList(GetQueuedFilingsRequest request) 
	{
		//TODO: replace with DAO
		return entityManager.createQuery(getCaseRecordsByStatus)
			.setParameter("statusCode",GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT)
			.setParameter("courtID",request.getCourtID())
			.getResultList();
	}
	

	public GetQueuedFilingsResponse getQueuedFilings(GetQueuedFilingsRequest request)
	{	
		try {		
			//we write to info because we don't persist this one in the DB.
			log.info(String.format("Running getQueuedFilings courtID:%s %s %s",request.getCourtID(),sessionService.getUsername(),this.getLevel()));			
						
			if(!courtAccessDefinitions.isValidCourtAccess(request.getCourtID()))
			{
				throw exnAccessDenied(request.getCourtID());	
			}
											
			List<String> caseRecords = getQueuedFilingsUuidList(request);
			ObjectFactory objectFactory = new ObjectFactory();
			GetQueuedFilingsResponse _return = objectFactory.createGetQueuedFilingsResponse();
			_return.setCases( objectFactory.createUuidList() );
			for(String crUuid : caseRecords)
			{
				_return.getCases().getUuid().add(crUuid);
			}
			
			return _return;
		}catch(Throwable thr)
		{
			String msg = thr.getMessage();
			faultLog.error("getQueuedFilings: ",thr);
			if(msg != null)
				throw exnError(msg);
			else
				throw exnError("Unknown error at getQueuedFilingsResponse");
		}
	}



	public ImportAckResponse importAck(ImportAckRequest request)
	{
		ObjectFactory objectFactory = new ObjectFactory();
		ImportAckResponse resp = objectFactory.createImportAckResponse();
		try {
			methodLog.info(
					String.format("importAck request {'caseNumber':'%s','reason':'%s','success':'%s','uuid':'%s','username':'%s','level':'%s'}",
							request.getAck().getCaseNum(),
							request.getAck().getReason(),
							request.getAck().getSuccess(),
							request.getAck().getUuid(),								
							sessionService.getUsername(),													
							this.getLevel()));
			
			if(log.isDebugEnabled()){
				log.debug("Running importAck");
			}
				
			boolean success = request.getAck().getSuccess().equalsIgnoreCase("1");
			
			// Get the case
			String caseUuid = request.getAck().getUuid();
			CaseRecord cr = null;
			try {
				cr = (CaseRecord) entityManager.createQuery(getCaseRecordByUuid)
					.setParameter("caseRecord", caseUuid)
					.getSingleResult();
			}catch(javax.persistence.NoResultException exn)
			{
				methodLog.info(
					String.format("importAck request {'caseNumber':'%s','reason':'%s','success':'%s','uuid':'%s','username':'%s','level':'%s'}",
							request.getAck().getCaseNum(),
							request.getAck().getReason(),
							request.getAck().getSuccess(),
							request.getAck().getUuid(),								
							sessionService.getUsername(),													
							this.getLevel()));
				throw exnCaseRecordNotFound(caseUuid);
			}	
			
			if(!courtAccessDefinitions.isValidCourtAccess(cr.getCourt().getId()))
			{
				throw exnAccessDenied(cr.getCourt().getId());
			}
				
			
			if(success)
			{
				//if the import was good,
				//update the case record with the CV Number and the status
				
				String cvNumber = request.getAck().getCaseNum();
				
				cr.setStatus((CaseRecordStatus) entityManager.createQuery("from CaseRecordStatus where description = :desc")
						.setParameter("desc","Imported").getSingleResult());
				
				if(cvNumber != null && !cvNumber.trim().equalsIgnoreCase(""))
				{
					// Update the CV NUmber
					cr.setCaseTrackingId(cvNumber);					
				}
				
				
				// Add an entry into the case record history for the setting of the judge
				if (request.getAck().getJudge() != null && !request.getAck().getJudge().trim().equalsIgnoreCase("")) {											
					CaseRecordHistory judge = new CaseRecordHistory();
					judge.setCaseRecord(cr);
					judge.setEventDateTime(new Date());
					judge.setPerson(null);
					judge.setEventType(new CaseRecordEventType(52,"Judge Assignment")); //TODO: add to GajeConstants.
					judge.setComment(request.getAck().getJudge());
					entityManager.persist(entityManager.merge(judge));
				}
				
				// Add an entry into the case record history for the importing of the case.
				//TODO: replace with FactoryMethod
				//TODO: replace with DAO
				CaseRecordHistory history = new CaseRecordHistory();			
				history.setCaseRecord(cr);
				history.setEventDateTime(new Date());
				history.setPerson(null);
				history.setEventType(new CaseRecordEventType(GajeConstants.CaseRecordEventType.FILING_IMPORTED_TO_CMS,""));
				history.setComment("");
				entityManager.persist(entityManager.merge(history));
				//caseRecordService.addHistoricalEvent(history);
				
				resp.setSuccess("1");
			}else{
				//If the import failed,
				// store the reason and the status
				
				//log the reason as well.
				rejectLog.info("Case was rejected by the court: " +
						"<br/>Reason: " + request.getAck().getReason() + 
						"<br/>Court: " + cr.getCourtUuid() + "" +
						"<br/>UUID: " + cr.getUuid() + 
						"<br/>Caption: " + cr.getCaseCaption());
						
				
				CaseRecordStatus st = (CaseRecordStatus) entityManager.createQuery("from CaseRecordStatus where description = :desc")
					.setParameter("desc","Rejected").getSingleResult();
				cr.setRejectionReason(request.getAck().getReason());
				cr.setStatus(st);
				
				//Then we store the case record history information.
				//TODO: replace with FactoryMethod
				//TODO: replace with DAO
				CaseRecordHistory history = new CaseRecordHistory();
				history.setCaseRecord(cr);
				history.setEventDateTime(new Date());
				history.setPerson(null);
				history.setEventType(new CaseRecordEventType(GajeConstants.CaseRecordEventType.FILING_NOT_ACCEPTED_BY_COURT_DUE_TO_TECHNICAL_ERROR,""));
				history.setComment("");
				entityManager.persist(entityManager.merge(history));
				//caseRecordService.addHistoricalEvent(history);
				
				//set the documents that were queued for import to failed if the importAck failed
				//TODO: replace with DAO
		        entityManager.createQuery("UPDATE Documents d set d.status.code = :code where caseRecord = :case and d.status.code = :currentCode")
		        	.setParameter("code", GajeConstants.DocumentStatus.REJECTED) 
		    		.setParameter("case", cr)
		    		.setParameter("currentCode", GajeConstants.DocumentStatus.QUEUED_FOR_IMPORT)    			
		    		.executeUpdate();
			
				resp.setSuccess("1");
			}
			
		}catch(SoapFault fault)
		{			
			faultLog.error("importAck: ",fault);			
			throw fault;
		}catch(Throwable thr)
		{
			resp.setSuccess("0");
			faultLog.error("importAck: ",thr);					
		}
		return resp;
	}
	
	public GetCaseFilingResponse getCaseFiling(GetCaseFilingRequest request)
	{
		
		methodLog.info(
				String.format("getCaseFiling request (%s) (%s) (%s)",
						request.getUuid(),														
						sessionService.getUsername(),													
						this.getLevel()));
		
		if(log.isDebugEnabled())
		{
			log.debug(String.format("Running getCaseFiling uuid:%s",request.getUuid()));
		}
		
		CaseRecord cr = null;
		try {
			//TODO: replace with DAO
			cr = (CaseRecord) entityManager.createQuery(getCaseRecordByUuid)
				.setParameter("caseRecord",request.getUuid())			
				.getSingleResult();

		}catch(NoResultException exn)
		{
			exn.printStackTrace();
			throw exnCaseRecordNotFound(request.getUuid());			
		}catch(EntityNotFoundException exn)
		{
			exn.printStackTrace();
			throw exnCaseRecordNotFound(request.getUuid());
		}
		
		if(!courtAccessDefinitions.isValidCourtAccess(cr.getCourt().getId()))
		{
			throw exnAccessDenied(cr.getCourt().getId());
		}	
		
		if(!caseFilingValidator.validateCaseFiling(cr))
		{
			throw exnInvalidCase(cr);
		}
		
		
		try {
			ObjectFactory objectFactory = new ObjectFactory();
			GetCaseFilingResponse _return = objectFactory.createGetCaseFilingResponse();
			converter.setEntityManager(this.entityManager);
			_return.setCaseFiling(converter.createCaseFiling(cr,cr.getCourt()));
			return _return;
		}catch(Throwable exn)
		{
			faultLog.error("getCaseFiling: ",exn);
			throw exnError(exn.getMessage());
		}
	}
	
	public boolean isStampCivilNumber(Documents doc)
	{
		return !doc.getCaseRecord().getCourt().getUuid().equalsIgnoreCase(
				GajeConstants.Courts.ChathamCounty);
	}
	public boolean isStampCourtStamp(Documents doc)
	{
		return !doc.getCaseRecord().getCourt().getUuid().equalsIgnoreCase(GajeConstants.Courts.ChathamCounty);
	}
	
	public boolean isStampCourtDate(Documents doc)
	{
		return doc.getCaseRecord().getCourt().getUuid().equals(GajeConstants.Courts.ClarkeCounty) &&
			(
					doc.getDocumentInstance().getCode() == GajeConstants.DocumentInstance.ClarkeCounty.NOTICE_OF_HEARING 
					||
					doc.getDocumentInstance().getCode() == GajeConstants.DocumentInstance.ClarkeCounty.NOTICE_AND_SUMMONS
			);
	}
	public GetDocumentsResponse getDocuments(GetDocumentsRequest parameters)
	{
		try {
			
			methodLog.info(
					String.format("getDocuments request (%s) (%s) (%s) (%s)",
							parameters.getCaseNum(),
							parameters.getUuid(),
							sessionService.getUsername(),													
							this.getLevel()));
			
			if(log.isDebugEnabled())
			{
				log.debug("running getDocuments implementation " + parameters.getCaseNum());
			}
			String caseUuid = parameters.getUuid();
			CaseRecord cr;
			try {
				 cr = (CaseRecord) entityManager.createQuery(getCaseRecordByUuid)
					.setParameter("caseRecord", caseUuid)
					.getSingleResult();
			}catch(NoResultException exn)
			{
				throw exnCaseRecordNotFound(parameters.getUuid());			
			}
			if(!courtAccessDefinitions.isValidCourtAccess(cr.getCourt().getId()))
			{
				throw exnAccessDenied(cr.getCourt().getId());
			}			
			
			List<Documents> dlist = cr.getDocuments();
			
			ObjectFactory f = new ObjectFactory();
			GetDocumentsResponse resp = f.createGetDocumentsResponse();		
			
			resp.setDocuments(f.createDocumentList());
			
			
			
			for(Documents doc : dlist)
			{
				if(doc.getStatus().getCode() == GajeConstants.DocumentStatus.QUEUED_FOR_IMPORT)
				{		
					
					if(isStampCourtDate(doc))
					{
						try 
						{
							this.getCourtDateStampController().stampDocument(doc);					
						}
						catch(StampingException exn)
						{
							faultLog.error("Court Date stamping failed!",exn);
						}
					}
					
					if(isStampCivilNumber(doc))
					{
						try 
						{													
							this.getCvNumberStampController().stampDocument(doc);
						}catch(StampingException exn)
						{
							faultLog.error("CVNumber stamping failed!",exn);
						}
					}
					
					if(isStampCourtStamp(doc))
					{
						try
						{						
							this.getCourtStampController().stampDocument(doc);
						}catch(StampingException exn)
						{
							faultLog.error("Court stamp failed!",exn);
						}	
					}
					
					entityManager.persist(entityManager.merge(doc));
					
									
					DocumentType dt;
					dt = (new ObjectFactory()).createDocumentType();
					
					dt.setType(doc.getDocumentInstance().getDocLocalCode().getCode());
					dt.setCourtID(doc.getCaseRecord().getCourt().getId());
					dt.setTitle(doc.getTitle().replace(" ","_"));
					dt.setContent(doc.getContent()); //base64 encoding is done automatically by the framework
					dt.setUuid(doc.getUuid());
						
					resp.getDocuments().getDocument().add(dt);					
				}
			}
			
			return resp;
		}catch(Throwable thr)
		{
			faultLog.error("getDocuments: ",thr);
			throw exnError("ERROR: " + thr.getMessage());
		}
	}

	public String putDocument(DocumentType document) 
	{
		try {
			methodLog.info(
					String.format("putDocument request (%s) (%s) (%s) (%s) (%s) (%s)",
							document.getCourtID(),
							document.getTitle(),
							document.getType(),
							document.getUuid(),								
							sessionService.getUsername(),													
							this.getLevel()));
			
			Documents d = (Documents) entityManager.createQuery("from Documents where uuid = :uuid")
				.setParameter("uuid",document.getUuid())
				.getSingleResult();
			if(!courtAccessDefinitions.isValidCourtAccess(d.getCaseRecord().getCourt().getId()))
			{
				throw exnAccessDenied(d.getCaseRecord().getCourt().getId());				
			}		
			//d.setContent(document.getContent());
			int newVersionNumber = d.getDocumentVersion() + 1;

			//Set the file structure.
			String path = d.getDocumentFolder();
			File newPath = new File(path);
			newPath.mkdirs();
			
			//Create the file.
			File newFileVersion = new File(d.getDocumentPathWithVersion(newVersionNumber));
			
			try {
				newFileVersion.createNewFile();
				FileOutputStream outStream = new FileOutputStream(newFileVersion);
				if (document.getContent() != null) {
					outStream.write(document.getContent());
					outStream.flush();

					//Insert the document into the documentBinaries table.
					DocumentBinaries entry = new DocumentBinaries();								
					
					entry.setUuid(UUID.randomUUID().toString());
					entry.setMetaDataUuid(d);
					entry.setVersion(newVersionNumber);
					entry.setPath(newFileVersion.getPath());
					
					entityManager.persist(entityManager.merge(entry));
				}
				outStream.close();
			} catch (FileNotFoundException fnf) {
				fnf.printStackTrace();
				throw exnError("File Not Found Error!");
			} catch (IOException io) {
				io.printStackTrace();
				throw exnError("IOError!");
			}
		}catch(NoResultException exn)
		{
			//throw exnDocumentNotFound(document.getUuid());
			return "0";
		}catch(SoapFault fault)
		{
			faultLog.error("putDocument: ",fault);
			throw fault;
		}catch(Throwable thr)
		{
			faultLog.error("putDocument: ",thr);
			return "0";
		}
		
		return "1";
	}
	
    public ResetResponse reset(ResetRequest parameters) { 
    	
    	log.debug("Executing operation reset");
        throw new javax.xml.ws.ProtocolException("Reset method is for debugging mode only!");
    }
    
    public DocAckResponse docAck(DocAckRequest parameters) { 
        ObjectFactory f = new ObjectFactory();
        DocAckResponse response = f.createDocAckResponse();      
        
    	try {
    	
			methodLog.info(
					String.format("docAck request (%s) (%s) (%s) (%s)",
							parameters.getCaseUuid(),
							parameters.getDocAck(),															
							sessionService.getUsername(),													
							this.getLevel()));			
    					
	    	log.debug("Executing operation docAck");
	        
	    
	        String caseUuid = parameters.getCaseUuid();
	        CaseRecord cr;
	    	try {
				 cr = (CaseRecord) entityManager.createQuery(getCaseRecordByUuid)
					.setParameter("caseRecord", caseUuid)
					.getSingleResult();
			}catch(NoResultException exn)
			{
				throw exnCaseRecordNotFound(parameters.getCaseUuid());			
			}
			if(!courtAccessDefinitions.isValidCourtAccess(cr.getCourt().getId()))
			{
				throw exnAccessDenied(cr.getCourt().getId());			
			}			
	        				      
	        if(parameters.getDocAck().equalsIgnoreCase("1")) //successful
	        {
	        	//set the documents that were queued for import as imported.
	        	entityManager.createQuery("UPDATE Documents d set d.status.code = :code where caseRecord.uuid = :case and d.status.code = :currentCode")
	        		.setParameter("code", GajeConstants.DocumentStatus.IMPORTED)
	        		.setParameter("case", parameters.getCaseUuid())
	        		.setParameter("currentCode", GajeConstants.DocumentStatus.QUEUED_FOR_IMPORT)
	        		.executeUpdate();
	 
	        	
	        	//update the versions to be the newest versions if we aren't in Chatham County (Who uses put document).
	        	if(!cr.getCourt().getUuid().equalsIgnoreCase(GajeConstants.Courts.ChathamCounty))
	        	{	        		
		        	for(Documents d  : cr.getDocuments())
		        	{	   
		        		/*
	        			DocumentBinaries db = new DocumentBinaries();	        			
	        			db.setMetaDataUuid(d);
	        			db.setPath(d.getDocumentPathWithVersion(d.getDocumentVersion()+1));
	        			db.setVersion(d.getDocumentVersion()+1);
	        			db.setUuid(UUID.randomUUID().toString());
	        			*/
	        			//if(new File(db.getPath()).exists() )
	        			//entityManager.persist(db);
	        			//entityManager.persist(d);
	        		}
		        	
	        	}	 
	        	
	        }else{
	        	//set the documents that were queued for import to failed if the dockument ack failed.
	        	entityManager.createQuery("UPDATE Documents d set d.status.code = :code where caseRecord.uuid = :case and d.status.code = :currentCode")
	        		.setParameter("code", GajeConstants.DocumentStatus.REJECTED) 
	    			.setParameter("case", parameters.getCaseUuid())
	    			.setParameter("currentCode", GajeConstants.DocumentStatus.QUEUED_FOR_IMPORT)    			
	    			.executeUpdate();
	        	
				//Then we store the case record history information.
	        	//TODO: replace with factory method
				CaseRecordHistory history = new CaseRecordHistory();
				history.setCaseRecord(cr);
				history.setEventDateTime(new Date());
				history.setPerson(null);
				history.setEventType(new CaseRecordEventType(18,""));
				entityManager.persist(entityManager.merge(history));
	        	
	        	//set the case as Rejected
	        	cr.setStatus(new CaseRecordStatus(GajeConstants.CaseRecordStatus.REJECTED,""));
	        	cr.setRejectionReason("Documents Not Accepted");	        	
	        }
	        response.setDocSuccess("1");
    	}catch(SoapFault fault)
    	{
    		faultLog.error("docAck: ",fault);
    		throw fault;
    	}catch(Throwable thr)
    	{
    		faultLog.error("docAck: ",thr);
    		response.setDocSuccess("0");
    	}
               
        return response;   
    }


	public static SoapFault exnCaseRecordNotFound(String id)
	{
		return new SoapFault(String.format("Case Record %s Not Found!",id),
				new QName("not_found"));
	}
	
	
	public void setConverter(DomToWebserviceConverter c)
	{
		this.converter = c;
	}

	public void setEntityManager(EntityManager em)
	{
		this.entityManager = em;
	}
	public void setCourtAccessDefinitions(CourtAccessDefinitions courtAccessDefinitions) {
		this.courtAccessDefinitions = courtAccessDefinitions;
	}

	public CourtAccessDefinitions getCourtAccessDefinitions() {
		return courtAccessDefinitions;
	}

	public void setServiceFactory(ServiceFactory serviceFactory) {
		this.serviceFactory = serviceFactory;
	}

	public ServiceFactory getServiceFactory() {
		return serviceFactory;
	}

	public void setCourtStampController(CourtStampController courtStampController) {
		this.courtStampController = courtStampController;
	}

	public CourtStampController getCourtStampController() {
		return courtStampController;
	}

	public void setCvNumberStampController(CVNumberStampController cvNumberStampController) {
		this.cvNumberStampController = cvNumberStampController;
	}

	public CVNumberStampController getCvNumberStampController() {
		return cvNumberStampController;
	}

	public void setCourtDateStampController(CourtDateStampController courtDateStampController) {
		this.courtDateStampController = courtDateStampController;
	}

	public CourtDateStampController getCourtDateStampController() {
		return courtDateStampController;
	}

}
