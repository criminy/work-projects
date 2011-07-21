package us.gaje.ws.test;

import static org.junit.Assert.assertEquals;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import us.gaje.service.CourtAccessDefinitions;
import us.gaje.service.impl.jpa.CourtAccessDefinitionsJPA;

/**
 * Test runner for the court access database.
 * 
 * TODO: enable spring support.
 * TODO: load settings from file
 * 
 * @author artripa
 *
 */
//@ContextConfiguration(locations = 
//	{ 
///		"/CourtAccessTest-context.xml"
//	}
//)
//@RunWith(SpringJUnit4ClassRunner.class)
public class CourtAccessTest {


	//@PersistenceContext
	private EntityManager entityManager;

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	public EntityManager getEntityManager() {
		return entityManager;
	}
	
    /**
     * Tests the JPA implementation of the CourtAccessDefinitions service
     * 
     */
    //@Test
    public void testJPAAuthentication()
    {
    	CourtAccessDefinitionsJPA jpa = new CourtAccessDefinitionsJPA();
    	jpa.setEntityManager(entityManager);
    	    	
    	test(jpa);
    }
    
    @Test
    public void nullTest()
    {
    	
    }
    
    private void test(CourtAccessDefinitions def)
    {
    	assertEquals(def.isValidCourtAccess("chathamCounty","fakeUser"),false);
    	assertEquals(def.isValidCourtAccess("fakeCourt","fakeUser"),false);
    	assertEquals(def.isValidCourtAccess("fakeCourt","chathamCounty"),false);
    	
    	assertEquals(def.isValidCourtAccess("chathamCounty","chathamCounty"),true);    	  
    	assertEquals(def.isValidCourtAccess("chathamCounty","CHATHAMCOUNTY"),true);
    	assertEquals(def.isValidCourtAccess("chathamcounty","CHATHAMCOUNTY"),true);
    	
    	assertEquals(def.isValidCourtAccess("CLARKECOUNTY","icon"),true);  
    	assertEquals(def.isValidCourtAccess("clarkeCounty","icon"),true);


    	assertEquals(def.isValidCourtAccess("cowetaCounty","ironData"),true);
    	assertEquals(def.isValidCourtAccess("cowetaCounty","icon"),false);
    	
    }

    
    
}
