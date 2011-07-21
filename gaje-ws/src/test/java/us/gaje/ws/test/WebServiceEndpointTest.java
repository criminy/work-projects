package us.gaje.ws.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import us.gaaoc.gajews.types.GetQueuedFilingsRequest;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:/Database-context.xml"})
public class WebServiceEndpointTest {

	//@PersistenceContext
	private EntityManager entityManager;


	@Test
	public void emptyTest()
	{
		
	}
	
	//@Test
	public void getQueuedFilings() {
		
		{// GetQueuedFilings should return more than 0 caseRecords on a non-fake court
			GetQueuedFilingsRequest request = new GetQueuedFilingsRequest();
			request.setCourtID("testCounty1");
			
			//GetQueuedFilingsResponse response = testService.getQueuedFilings(request);
			//org.junit.Assert.assertFalse(response.getCases().getUuid().size() == 0);
		}
		
		
		
		{// GetQueuedFilings should return 0 caseRecords on a fake court
			GetQueuedFilingsRequest request = new GetQueuedFilingsRequest();
			request.setCourtID("testCountyBlank");
			
			//GetQueuedFilingsResponse response = testService.getQueuedFilings(request);
			//org.junit.Assert.assertTrue(response.getCases().getUuid().size() == 0);
		}
		
	}

	

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}

	
	
}
