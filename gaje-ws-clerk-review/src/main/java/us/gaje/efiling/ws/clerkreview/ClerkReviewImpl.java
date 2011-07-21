package us.gaje.efiling.ws.clerkreview;


import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.framework.model.Documents;
import us.gaaoc.framework.model.constants.GajeConstants;
import us.gaaoc.framework.model.constants.GajeConstants.DocumentStatus;
import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaaoc.gajews.types.DocumentList;
import us.gaaoc.gajews.types.DocumentType;
import us.gaaoc.gajews.types.NewDocumentType;
import us.gaaoc.gajews.types.UuidList;
import us.gaje.service.GajeCaseFilingService;
import us.gaje.service.GajeConstantsService;
import us.gaje.service.exceptions.ObjectNotFound;

/**
 * Business logic implementation of the ClerkReview interface.
 * 
 * @author artripa
 *
 */


// Design considerations:
//
// 	1) Security and authorization checks are taken care of outside of this class (ClerkrReviewCaseStatusProxy and ClerkReviewSecurityProxy)
//	2) Runtime Errors are handled outside of this class (ClerkReviewErrorHandlingProxy) 
//
// This is just business logic, what steps are taken when.
//

public class ClerkReviewImpl implements ClerkReview {

	@Autowired(required=true) private GajeCaseFilingService gajeCaseFilingService;
	@Autowired(required=true) private GajeConstantsService gajeConstantsService;
	
	@Transactional	
	public UuidList getQueuedFilings(String courtID) {
		UuidList l = new UuidList();
		
		for(GajeCaseFiling cr : gajeCaseFilingService.byCourtIdAndStatus(courtID,GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT))
		{
			l.getUuid().add(cr.getInternalId());
		}
		
		return l;
	}

	@Transactional
	public CaseFiling getCaseFiling(String uuid) {
		GajeCaseFiling cf = gajeCaseFilingService.find(uuid);
		return cf.getTransmissionCaseFiling();
	}
	
	@Transactional
	public void rejectFiling(String uuid, String rejectionReason) {			
		GajeCaseFiling cf = gajeCaseFilingService.find(uuid);
		cf.rejectByClerk(rejectionReason);
		cf.save();
	}

	@Transactional
	public void acceptFiling(String uuid, String docket, String judge,
			XMLGregorianCalendar courtDate, DocumentList documents) 
	{
		GajeCaseFiling cf = gajeCaseFilingService.find(uuid);
		
		cf.acceptByClerk();
		cf.docket(docket);
		
		if(judge != null && !judge.equalsIgnoreCase(""))	
			cf.updateJudge(judge);
		
		if(courtDate != null)
			cf.setCourtDate(courtDate.toGregorianCalendar().getTime());			
		
		if(documents != null) {
			for(DocumentType dt : documents.getDocument())
			{			
				cf.updateDocument(dt.getUuid(),dt.getContent());
			}
		}
		
		cf.save();
	}
	
	@Transactional
	public String addDocument(String uuid, NewDocumentType document) 
	{
		//file all documents sent from the court as local type code 'NON_DHS'
		// and use the court-specified title as the title
		// for the document.
		GajeCaseFiling cf = gajeCaseFilingService.find(uuid);
		Documents ret = cf.addDocument(document.getTitle(),"NON_DHS",document.getContent(),gajeConstantsService.getDocumentStatus(DocumentStatus.IMPORTED));	
		cf.save();
		return ret.getUuid();
	}
	
	
	public void setGajeCaseFilingService(
			GajeCaseFilingService gajeCaseFilingService) {
		this.gajeCaseFilingService = gajeCaseFilingService;
	}

	public void setGajeConstantsService(GajeConstantsService gajeConstantsService) {
		this.gajeConstantsService = gajeConstantsService;
	}
}
