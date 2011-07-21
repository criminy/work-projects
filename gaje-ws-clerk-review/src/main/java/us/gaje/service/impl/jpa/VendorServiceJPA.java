package us.gaje.service.impl.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Service;

import us.gaje.model.Vendor;
import us.gaje.service.VendorService;

@Service("vendorService")
public class VendorServiceJPA implements VendorService{

	@PersistenceContext private EntityManager entityManager;
	
	public List<Vendor> find(String _courtID, String _user) {
		String user = _user.toLowerCase();
		String courtID = _courtID.toLowerCase();
		@SuppressWarnings("unchecked") List<Vendor> vendors = 
			(List<Vendor>) entityManager.createQuery(
					"from Vendor where lower(court.id) = lower(:court) and lower(username) = lower(:user)")
						.setParameter("court", courtID)
						.setParameter("user", user)
						.getResultList();
		return vendors;
	}
	
	
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
