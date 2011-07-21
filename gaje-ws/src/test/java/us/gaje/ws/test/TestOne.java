package us.gaje.ws.test;

import org.junit.Test;


public class TestOne {

	/*
   private static Logger logger = Logger.getLogger(TestOne.class.getName());

    private EntityManagerFactory emFactory;

    private EntityManager em;

    private Connection connection;
	*/
    @Test
    public void emptyTest()
    {    	
    }
    
/**
 * Uses an invalid court ID to test that 
 * getQueuedFilings doesn't return cases
 * when it shouldn't
 */
    /*
	@DisabledTest public void testGetQueuedFilingsInvalidCourt() throws Exception
	{		
		us.gaaoc.gajews_impl.GajeWS_QueuedFilings impl = new us.gaaoc.gajews_impl.GajeWS_QueuedFilings();
		GetQueuedFilingsRequest req = new GetQueuedFilingsRequest();
		impl.setEntityManager(em);
		impl.setConverter(new DomToWebserviceConverter());

		req.setCourtID("INVALID_COURT_ID");
		if(em == null) throw new Exception("Entity manager is null");
		GetQueuedFilingsResponse resp = impl.getQueuedFilings(req);
		Assert.assertEquals(resp.getCases().getUuid().size(),0);
	}

	@DisabledTest public void testInvalidPutDocument() throws Exception
	{		
		us.gaaoc.gajews_impl.GajeWS_QueuedFilings impl = new us.gaaoc.gajews_impl.GajeWS_QueuedFilings();
		PutDocument req = new PutDocument();
		DocumentType t = new DocumentType();
		t.setContent("".getBytes());
		t.setCourtID("COURTID1");
		t.setTitle("TITLE1");
		t.setType("121212");
		t.setUuid("INVALID_UUID");
		
		impl.setEntityManager(em);
		impl.setConverter(new DomToWebserviceConverter());

		try {
			impl.putDocument(t);
			Assert.assertEquals(1,0); //This should never run.
		}catch(java.lang.RuntimeException exn)
		{
			
		}
		
		
	}
	
	@DisabledTest public void testBarID() throws Exception
	{		
		Court c = new Court();
		Person p = new Person();
		p.setUuid("personUuid");
		c.setUuid("courtUuid");
		em.createQuery("select o.organization from PersonOrganizations o where o.court = :court and o.person = :person")
		.setParameter("court",c.getUuid())
		.setParameter("person", p)
		.getResultList();
	}
	
/**
 * Uses a valid courtID to test that
 * getQueuedFilings returns cases
 * when it should.
 
	@DisabledTest public void testGetQueuedFilingsSome() throws Exception
	{
		us.gaaoc.gajews_impl.GajeWS_QueuedFilings impl = new us.gaaoc.gajews_impl.GajeWS_QueuedFilings();
		GetQueuedFilingsRequest req = new GetQueuedFilingsRequest();
		impl.setEntityManager(em);
		impl.setConverter(new DomToWebserviceConverter());
		req.setCourtID("COURTID");
		GetQueuedFilingsResponse resp = impl.getQueuedFilings(req);
		Assert.assertEquals(resp.getCases().getUuid().size(),1);
		Assert.assertEquals(resp.getCases().getUuid().get(0),"caseRecordTest01");
	}

	//@Before
	public void setup() throws Exception
	{
        try {
            logger.info("Building JPA EntityManager for unit tests");
            emFactory = Persistence.createEntityManagerFactory("gaje");
            em = emFactory.createEntityManager();

        } catch (Exception ex) {
            //fail("Exception during JPA EntityManager instanciation.");
            //throw ex;
        }

	}

	//@After
	public void after() throws Exception
	{

		logger.info("Shuting down Hibernate JPA layer.");
		if (em != null) {
		    em.close();
		}
		if (emFactory != null) {
		    emFactory.close();
		}
	}
	*/
}
