package us.gaje.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.service.web.SessionService;

/**
 * 
 * Implementation of CourtAccessDefinitions.
 * 
 * @author artripa
 * @see us.gaje.model.Vendor
 *
 */
@Component("courtAccessDefinitions")
public class CourtAccessDefinitionsImpl implements CourtAccessDefinitions 
{

	@Autowired(required=true) private SessionService sessionService;
	@Autowired(required=true) private VendorService vendorService;
	
	public boolean isValidCourtAccess(String courtID) {
		String username = sessionService.getUsername();
		return isValidCourtAccess(courtID,username);
	}

	@Transactional
	public boolean isValidCourtAccess(String courtID, String user) {		
		if(vendorService.find(courtID,user).size() != 0)
		{
			return true;
		}
		return false;
	}
	
	public SessionService getSessionService() {
		return sessionService;
	}
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}

}
