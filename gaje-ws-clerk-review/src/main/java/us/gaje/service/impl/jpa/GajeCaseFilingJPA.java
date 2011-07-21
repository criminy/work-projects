package us.gaje.service.impl.jpa;

import java.util.Date;

import javax.persistence.EntityManager;

import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.framework.model.CaseRecord;
import us.gaaoc.framework.model.CaseRecordHistory;
import us.gaaoc.framework.model.CourtEvents;
import us.gaaoc.framework.model.DocumentInstance;
import us.gaaoc.framework.model.DocumentStatus;
import us.gaaoc.framework.model.DocumentType;
import us.gaaoc.framework.model.Documents;
import us.gaaoc.framework.model.Person;
import us.gaaoc.framework.model.constants.GajeConstants;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaje.efiling.ws.clerkreview.GajeCaseFiling;
import us.gaje.service.GajeConstantsService;
import us.gaje.service.exceptions.ObjectNotFound;

/**
 * JPA Implementation of the CaseFiling business object.
 * 
 * @author artripa
 *
 */

//TODO: this class should not depend on JPA and should instead use repositories 
//	which can be plugged/unplugged based on backend requirements.
class GajeCaseFilingJPA implements GajeCaseFiling {

	private CaseRecord cr;
	
	private GajeConstantsService gajeConstantsService;
	private DomToWebserviceConverterJPA domToWebserviceConverter;
	EntityManager em;
	
	@Transactional
	public void addHistoricalRecord(Date date, Person p,int historyId, String comment) {
		CaseRecordHistory history = new CaseRecordHistory();			
		history.setCaseRecord(em.merge(this.cr));		
		history.setEventDateTime(date);
		history.setPerson(p);
		history.setEventType(gajeConstantsService.getCaseRecordEventType(historyId));
		history.setComment(comment);
		em.persist(history);
		this.cr.getCaseRecordHistory().add(history);		
	}
	
	
	@Transactional
	public void rejectByClerk(String rejectionReason) {
		cr.setRejectionReason(rejectionReason);
		cr.setStatus(gajeConstantsService.getCaseRecordStatus(GajeConstants.CaseRecordStatus.REJECTED));
		for(Documents d : cr.getDocuments())
		{
			if(d.getStatus().getCode() == GajeConstants.DocumentStatus.QUEUED_FOR_IMPORT)
			{
				d.setStatus(gajeConstantsService.getDocumentStatus(GajeConstants.DocumentStatus.REJECTED));
			}
		}
		this.addHistoricalRecord(new Date(),null,GajeConstants.CaseRecordEventType.FILING_REJECTED,rejectionReason);
	}

	@Transactional
	public void acceptByClerk() {
		cr.setStatus(gajeConstantsService.getCaseRecordStatus(GajeConstants.CaseRecordStatus.IMPORTED));
		for(Documents d : cr.getDocuments())
		{
			if(d.getStatus().getCode() == GajeConstants.DocumentStatus.QUEUED_FOR_IMPORT)
			{
				d.setStatus(gajeConstantsService.getDocumentStatus(GajeConstants.DocumentStatus.IMPORTED));
			}
		}
		this.addHistoricalRecord(new Date(),null,GajeConstants.CaseRecordEventType.FILING_IMPORTED_TO_CMS,"");
	}

	@Transactional
	public void setCourtDate(Date courtDate) {
		CourtEvents ecourtDate = new CourtEvents();
		ecourtDate.setEventType(gajeConstantsService.getCourtEventType(GajeConstants.CourtEventType.COURT_DATE));
		ecourtDate.setEventTime(courtDate);
		ecourtDate.setPerson(null);
		ecourtDate.setCaseRecord(cr);
		em.persist(ecourtDate);
		this.cr.getCourtEvents().add(ecourtDate);
	}
	
	@Transactional
	public void updateDocument(String uuid, byte[] content) {
		Documents d = em.find(Documents.class,uuid);
		if(d == null) {
			throw new ObjectNotFound("Document " + uuid + " Not Found!");
		}else{
			d.setContent(content);
			em.persist(d);
		}
	}
	
	@Transactional
	public Documents addDocument(String title, String type, byte[] content,DocumentStatus status) {
		
		Documents d = new Documents();
		d.setTitle(title);
		d.setCaseRecord(cr);
		d.setPerson(null);

		d.setPhysicalType("pdf");
		
		d.setReceivedDateTime(new Date());
		d.setStatus(status);
		
		DocumentInstance di = gajeConstantsService.getDocumentInstanceByLocalCode(type, cr.getCourt());
		
		d.setDocumentInstance(di);
		em.persist(d);
		
		d.setContent(content);
		
		this.cr.getDocuments().add(d);
		
		return d;
	}
	
	@Transactional
	public void docket(String docketNumber) {
		cr.setCaseTrackingId(docketNumber);		
	}
	
	
	@Transactional
	public void updateJudge(String judge) {		
		this.addHistoricalRecord(new Date(),null,GajeConstants.CaseRecordEventType.FILING_IMPORTED_TO_CMS,judge);
	}
	
	public void save() {
		em.persist(cr);
	}
	
	
	public CaseFiling getTransmissionCaseFiling() {
		return domToWebserviceConverter.createCaseFiling(cr,cr.getCourt());
	}
	
	
	public String getInternalId() {
		return cr.getUuid();
	}

	
	public CaseRecord getCaseRecord() {
		return cr;
	}

	public void setCaseRecord(CaseRecord cr) {
		this.cr = cr;
	}


	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	public void setDomToWebserviceConverter(
			DomToWebserviceConverterJPA domToWebserviceConverter) {
		this.domToWebserviceConverter = domToWebserviceConverter;
	}
	
	public void setGajeConstantsService(
			GajeConstantsService gajeConstantsService) {
		this.gajeConstantsService = gajeConstantsService;
	}
	
}
