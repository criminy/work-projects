package us.gaje.service.impl.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.model.Vendor;
import us.gaje.service.CourtAccessDefinitions;
import us.gaje.service.web.SessionService;

/**
 * JPA implementation of the courtaccessdefinitions
 * 
 * Reads from JPA entity Vendor.
 * 
 * @author artripa
 * @see us.gaje.model.Vendor
 *
 */
public class CourtAccessDefinitionsJPA implements CourtAccessDefinitions {
	private SessionService sessionService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public EntityManager getEntityManager() {
		return entityManager;
	}
	public SessionService getSessionService() {
		return sessionService;
	}
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}


	public boolean isValidCourtAccess(String courtID) {
		String username = sessionService.getUsername();
		return isValidCourtAccess(courtID,username);
	}

	@Transactional
	public boolean isValidCourtAccess(String _courtID, String _user) {
		String user = _user.toLowerCase();
		String courtID = _courtID.toLowerCase();
		
		@SuppressWarnings("unchecked") List<Vendor> vendors = 
			(List<Vendor>) entityManager.createQuery(
					"from Vendor where lower(court.id) = lower(:court) and lower(username) = lower(:user)")
						.setParameter("court", courtID)
						.setParameter("user", user)
						.getResultList();
		if(vendors.size() != 0)
		{
			return true;
		}
		return false;
	}

}
