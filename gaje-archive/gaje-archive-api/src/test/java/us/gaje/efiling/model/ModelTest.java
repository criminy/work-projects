package us.gaje.efiling.model;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;


import org.junit.Test;

import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.model.enums.CaseStatus;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:META-INF/spring/database-context.xml"})
//@TransactionConfiguration(defaultRollback=true)
public class ModelTest {

	//@PersistenceContext
	private EntityManager entityManager;
		
	private DaoManager daoManager;
	
	//@Before
	//@Transactional
	public void createDatabase()
	{
		
		daoManager = new DaoManager();
		daoManager.setEntityManager(entityManager);
		
		Court court = new Court();
		court.setName("a");
		court.setExternalId("externalId1");
		entityManager.persist(court);
		
		
		Person witness = new Person();
		witness.setFirstName("witness");
		witness.setLastName("witness1");
		entityManager.persist(witness);
		
		Person child1 = new Person();
		child1.setFirstName("child");
		child1.setLastName("child1");
		entityManager.persist(child1);
		
		CaseRecord cr = new CaseRecord();
		cr.setStatus(CaseStatus.ReceivedByAttorney);
		cr.setCaseTrackingId("");
		cr.setCourt(court);
		entityManager.persist(cr);

		//cr.getPlaintiffs().add(child1);
	//	cr.getWitnesses().add(witness);
		
		entityManager.merge(cr);
		
	}
	
	
	//@Test
	@Transactional
	public void initialtest()
	{	
		assertNotNull(this.entityManager);
		assertEquals(daoManager.get(CaseRecord.class).all().size(),1);
		assertEquals(daoManager.get(Court.class).all().size(),1);
		
		CaseRecord cr = daoManager.get(CaseRecord.class).all().get(0);
		//assertEquals(cr.getPlaintiffs().size(),1);
		//assertEquals(cr.getWitnesses().size(),1);
		assertEquals(cr.getStatus(),CaseStatus.ReceivedByAttorney);
		//assertEquals(cr.getDefendantAttornies().size(),0);
		//assertEquals(cr.getDefendantAttornies().size(),0);
		//assertEquals(cr.getDefendants().size(),0);
		
	}
	
	@Test
	public void dummyTest()
	{
		
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}
	
	public DaoManager getDaoManager() {
		return daoManager;
	}
}
