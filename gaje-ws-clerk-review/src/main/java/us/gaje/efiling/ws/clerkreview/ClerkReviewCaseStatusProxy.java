package us.gaje.efiling.ws.clerkreview;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;

import us.gaaoc.framework.model.constants.GajeConstants;
import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaaoc.gajews.types.DocumentList;
import us.gaaoc.gajews.types.NewDocumentType;
import us.gaaoc.gajews.types.UuidList;
import us.gaje.service.GajeCaseFilingService;
import us.gaje.service.GajeConstantsService;
import us.gaje.service.exceptions.ObjectNotFound;

/**
 * Verifies the case is in the correct status.
 * 
 * @author artripa
 *
 */
public class ClerkReviewCaseStatusProxy extends ClerkReviewDelegatingProxy implements ClerkReview {

	@Autowired(required=true) private GajeCaseFilingService gajeCaseFilingService;

	@Override
	public void acceptFiling(String uuid, String docket, String judge,
			XMLGregorianCalendar courtDate, DocumentList documents) {
		GajeCaseFiling cf = gajeCaseFilingService.find(uuid);
		
		if(cf.getCaseRecord().getStatus().getCode() != GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT)
		{
			throw new ObjectNotFound("Case Record with id " + uuid + " is not prepared for import.");
		}
		
		super.acceptFiling(uuid, docket, judge, courtDate, documents);
	}
	
	@Override
	public CaseFiling getCaseFiling(String uuid) {
		GajeCaseFiling cf = gajeCaseFilingService.find(uuid);
		
		if(cf.getCaseRecord().getStatus().getCode() != GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT)
		{
			throw new ObjectNotFound("Case Record with id " + uuid + " is not prepared for import.");
		}
		
		return super.getCaseFiling(uuid);
	}

	@Override
	public void rejectFiling(String uuid, String rejectionReason) {
		GajeCaseFiling cf = gajeCaseFilingService.find(uuid);
		
		if(cf.getCaseRecord().getStatus().getCode() != GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT)
		{
			throw new ObjectNotFound("Case Record with id " + uuid + " is not prepared for import.");
		}
		
		super.rejectFiling(uuid, rejectionReason);
	}
	
	public void setGajeCaseFilingService(
			GajeCaseFilingService gajeCaseFilingService) {
		this.gajeCaseFilingService = gajeCaseFilingService;
	}
	
}
