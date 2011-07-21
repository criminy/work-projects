package us.gaje.efiling.ws.clerkreview;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.framework.model.Court;
import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaaoc.gajews.types.DocumentList;
import us.gaaoc.gajews.types.NewDocumentType;
import us.gaaoc.gajews.types.UuidList;
import us.gaje.service.GajeCaseFilingService;
import us.gaje.service.CourtAccessDefinitions;
import us.gaje.service.exceptions.ObjectNotFound;

/**
 * Implementation of clerkReview which uses the backend 
 * database to check for valid user and court permissions before
 * delegating to the backend implementation.
 * 
 * @see ClerkReview
 * @author artripa
 *
 */
public class ClerkReviewSecurityProxy extends ClerkReviewDelegatingProxy implements ClerkReview{

	Logger log = Logger.getLogger(ClerkReviewSecurityProxy.class);	
	@Autowired(required=true) private CourtAccessDefinitions courtAccessDefinitions;
	@Autowired(required=true) private GajeCaseFilingService gajeCaseFilingService;
	

	
	@Transactional
	public UuidList getQueuedFilings(String courtID) {		
		if(courtAccessDefinitions.isValidCourtAccess(courtID))
		{
			return super.getQueuedFilings(courtID);
		}else{
			throw new ObjectNotFound("Can't find Court with ID " + courtID);
		}
	}

	@Transactional
	public CaseFiling getCaseFiling(String uuid) {
		Court c = gajeCaseFilingService.find(uuid).getCaseRecord().getCourt();
		if(courtAccessDefinitions.isValidCourtAccess(c.getId()))
		{
			return super.getCaseFiling(uuid);
		}else{
			throw new ObjectNotFound("Can't find Case Record with ID " + uuid);
		}
	}

	@Transactional
	public void rejectFiling(String uuid, String rejectionReason) {
		Court c = gajeCaseFilingService.find(uuid).getCaseRecord().getCourt();
		if(courtAccessDefinitions.isValidCourtAccess(c.getId()))
		{
			super.rejectFiling(uuid,rejectionReason);
		}else{
			throw new ObjectNotFound("Can't find Case Record with ID " + uuid);
		}
	}
	
	@Transactional
	public void acceptFiling(String uuid, String docket, String judge,
			XMLGregorianCalendar courtDate, DocumentList documents) {
		Court c = gajeCaseFilingService.find(uuid).getCaseRecord().getCourt();
		if(courtAccessDefinitions.isValidCourtAccess(c.getId()))
		{
			super.acceptFiling(uuid,docket,judge,courtDate,documents);
		}else{
			throw new ObjectNotFound("Can't find Case Record with ID " + uuid);
		}
	}

	@Transactional
	public String addDocument(String uuid, NewDocumentType document)  {
		Court c = gajeCaseFilingService.find(uuid).getCaseRecord().getCourt();
		if(courtAccessDefinitions.isValidCourtAccess(c.getId()))
		{
			return super.addDocument(uuid,document);
		}else{
			throw new ObjectNotFound("Can't find Case Record with ID " + uuid);
		}
	}
	
	
	
	
	
	
	public void setCourtAccessDefinitions(
			CourtAccessDefinitions courtAccessDefinitions) {
		this.courtAccessDefinitions = courtAccessDefinitions;
	}
	
	public void setGajeCaseFilingService(
			GajeCaseFilingService gajeCaseFilingService) {
		this.gajeCaseFilingService = gajeCaseFilingService;
	}

	
}
