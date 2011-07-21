package us.gaje.efiling.ws.clerkreview;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.LinkedList;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.framework.model.CaseRecord;
import us.gaaoc.framework.model.CaseRecordHistory;
import us.gaaoc.framework.model.CaseRecordStatus;
import us.gaaoc.framework.model.CaseRecordType;
import us.gaaoc.framework.model.Court;
import us.gaaoc.framework.model.CourtEvents;
import us.gaaoc.framework.model.Documents;
import us.gaaoc.framework.model.LocalCaseCategory;
import us.gaaoc.framework.model.LocalCaseDivision;
import us.gaaoc.framework.model.LocalCaseFilingType;
import us.gaaoc.framework.model.LocalCaseType;
import us.gaaoc.framework.model.OrgParticipants;
import us.gaaoc.framework.model.Person;
import us.gaaoc.framework.model.constants.GajeConstants;
import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaaoc.gajews.types.UuidList;
import us.gaje.service.GajeCaseFilingService;
import us.gaje.service.exceptions.ObjectNotFound;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;

/**
 * Unit test for the core clerk review implementation.
 * 
 * @author artripa
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/ClerkReviewImplTest.xml"})
@Transactional
public class ClerkReviewImplTest {

	@Resource private ClerkReview clerkReviewImpl;
	@PersistenceContext private EntityManager entityManager;
	@Resource private GajeCaseFilingService gajeCaseFilingService;	
	
	/*
	@Before
	public void setup()
	{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("ClerkReviewImplTest.xml");
		EntityManagerFactory fact = (EntityManagerFactory) ctx.getBeansOfType(EntityManagerFactory.class).values().iterator().next();
		clerkReview = (ClerkReview) ctx.getBean("clerkReviewImpl");
		gajeCaseFilingService = (GajeCaseFilingService) ctx.getBean("gajeCaseFilingService");
		entityManager = fact.createEntityManager();
	}
	*/
	
	public void setClerkReviewImpl(ClerkReview clerkReviewImpl) {
		this.clerkReviewImpl = clerkReviewImpl;
	}
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	public void setGajeCaseFilingService(
			GajeCaseFilingService gajeCaseFilingService) {
		this.gajeCaseFilingService = gajeCaseFilingService;
	}

	protected LocalCaseFilingType getLocalCaseFilingType(String courtUuid)
	{
		return (LocalCaseFilingType) entityManager.createQuery("from LocalCaseFilingType where court.uuid = :court and cmsCode = 121110")
			.setParameter("court",courtUuid)
			.getSingleResult();
	}
	
	protected LocalCaseCategory getLocalCaseCategory(String courtUuid)
	{
		return (LocalCaseCategory) entityManager.createQuery("from LocalCaseCategory where court.uuid = :court and cmsCode = 121150")
			.setParameter("court",courtUuid)
			.getSingleResult();
	}
	
	protected LocalCaseDivision getLocalCaseDivision(String courtUuid)
	{
		return (LocalCaseDivision) entityManager.createQuery("from LocalCaseDivision where court.uuid = :court and cmsCode = 12")
		.setParameter("court",courtUuid)
		.getSingleResult();
	}
	
	
	protected LocalCaseType getLocalCaseType(String courtUuid)
	{
		return (LocalCaseType) entityManager.createQuery("from LocalCaseType where court.uuid = :court and cmsCode = 122110")
		.setParameter("court",courtUuid)
		.getSingleResult();
	}
	
	
	@Transactional
	protected String generateFiling(String courtUuid,int status)
	{
		CaseRecord cr = new CaseRecord();
		cr.setCourt(new Court(courtUuid));
		cr.setStatus(new CaseRecordStatus(status,""));
		cr.setCaseType(new CaseRecordType(1,""));
		cr.setLocalCaseCategory(getLocalCaseCategory(courtUuid));
		cr.setLocalCaseDivision(getLocalCaseDivision(courtUuid));
		cr.setLocalCaseFilingType(getLocalCaseFilingType(courtUuid));
		cr.setLocalCaseType(getLocalCaseType(courtUuid));
		cr.setInstantiationDateTime(new Date());
		cr.setSubmittedDateTime(new Date());
		cr.setCaseRecordHistory(new HashSet<CaseRecordHistory>());
		cr.setDocuments(new LinkedList<Documents>());
		cr.setCourtEvents(new LinkedList<CourtEvents>());
		cr.setDefendants(new HashSet<Person>());
		cr.setPlaintiffs(new HashSet<Person>());
		cr.setWitnesses(new HashSet<Person>());
		cr.setOrgParticipants(new LinkedList<OrgParticipants>());
		cr.setPlaintiffAttorney(new HashSet<Person>());
		cr.setDefendantAttorney(new HashSet<Person>());
		
		entityManager.persist(cr);
		return cr.getUuid();
		
	}
	
	@Test
	public void acceptFilingTest()
	{			
		{//test with no additional data
			GajeCaseFiling cf = gajeCaseFilingService.find(generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT));
			
			assertNotNull(cf);
			assertNotNull(cf.getCaseRecord());
			assertNotNull(cf.getCaseRecord().getCaseRecordHistory());
			
			int caseRecordHistorySize = cf.getCaseRecord().getCaseRecordHistory().size();
			
			clerkReviewImpl.acceptFiling(cf.getInternalId(), "10TESTOX00001",null,null,null);
			
			cf = gajeCaseFilingService.find(cf.getInternalId());
			
			assertEquals("10TESTOX00001",cf.getCaseRecord().getCaseTrackingId()); // the docket number should be set
			assertEquals("courtUuidTesting",cf.getCaseRecord().getCourt().getUuid()); //court uuid should be test
			assertEquals(caseRecordHistorySize + 1,cf.getCaseRecord().getCaseRecordHistory().size()); // history should be increased by 1
			
			{ //check history			
				assertEquals(GajeConstants.CaseRecordEventType.FILING_IMPORTED_TO_CMS,
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getEventType().getCode() );
				assertEquals(cf.getCaseRecord().getUuid(),
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getCaseRecord().getUuid());
				assertEquals(null,
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getPerson() );
			}
			
			{// check court date 
				assertEquals(null,cf.getCaseRecord().getCourtDate());				
			}			
		}
	}
	
	@Test(expected=ObjectNotFound.class)
	public void acceptFilingOfANonExistentCase()
	{		
		clerkReviewImpl.acceptFiling("non-existent-case", "10TESTOX00002",null,null,null);					
	}	
	
	@Test
	public void acceptFilingTestWithCourtDate() {
		{//test with court date
			GajeCaseFiling cf = gajeCaseFilingService.find(generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT));
			
			
			XMLGregorianCalendar cal = new XMLGregorianCalendarImpl(new GregorianCalendar());
			
			int caseRecordHistorySize = cf.getCaseRecord().getCaseRecordHistory().size();
			
			clerkReviewImpl.acceptFiling(cf.getInternalId(), "10TESTOX00003",null,cal,null);
			
			cf = gajeCaseFilingService.find(cf.getInternalId());
			
			assertEquals("10TESTOX00003",cf.getCaseRecord().getCaseTrackingId()); // the docket number should be set
			assertEquals("courtUuidTesting",cf.getCaseRecord().getCourt().getUuid()); //court uuid should be test
			assertEquals(caseRecordHistorySize + 1,cf.getCaseRecord().getCaseRecordHistory().size()); // history should be increased by 1
			
			{ //check history			
				assertEquals(GajeConstants.CaseRecordEventType.FILING_IMPORTED_TO_CMS,
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getEventType().getCode() );
				assertEquals(cf.getCaseRecord().getUuid(),
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getCaseRecord().getUuid());
				assertEquals(null,
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getPerson() );
			}
			
			{// check court date 
				assertNotNull(cal);
				assertNotNull(cal.toGregorianCalendar());
				assertNotNull(cal.toGregorianCalendar().getTime());
				assertNotNull(cf);
				assertNotNull(cf.getCaseRecord());
				assertNotNull(cf.getCaseRecord().getCourtDate());	
				assertEquals(cal.toGregorianCalendar().getTime(),cf.getCaseRecord().getCourtDate().getEventTime());
				assertEquals(GajeConstants.CourtEventType.COURT_DATE,cf.getCaseRecord().getCourtDate().getEventType().getCode());				
			}			
		}
	}
	
	@Test(expected=ObjectNotFound.class)
	public void acceptFilingOfANonQueuedCase()
	{
		GajeCaseFiling cf = gajeCaseFilingService.find(generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.IMPORTED));
					
		XMLGregorianCalendar cal = new XMLGregorianCalendarImpl(new GregorianCalendar());
		
		int caseRecordHistorySize = cf.getCaseRecord().getCaseRecordHistory().size();	
		clerkReviewImpl.acceptFiling(cf.getInternalId(), "10TESTOX00004",null,null,null);
	}

	@Test
	public void getQueuedFilingsTest()
	{
		generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.IMPORTED);
		generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.IMPORTED);
		generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT);
		generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.IMPORTED);
		generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.IMPORTED);
		generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.IMPORTED);
		
		UuidList l = clerkReviewImpl.getQueuedFilings("testCounty");
		assertEquals(1,l.getUuid().size());
	}
	
	@Test
	public void getQueuedFilingsTestOfNonExistent()
	{
		UuidList l = clerkReviewImpl.getQueuedFilings("fakeCoutny");
		assertEquals(0,l.getUuid().size());
	}
	
	
	//TODO: broken test?!
	public void getCaseFilingTest()
	{
		String uuid = generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT);
		CaseFiling cf = clerkReviewImpl.getCaseFiling(uuid);
		
		
		
		assertEquals("testCounty",cf.getCourtID());
		assertEquals("",cf.getCaption());		
	}
	@Test(expected=ObjectNotFound.class)
	public void getCaseFilingNonExistentTest()
	{
		clerkReviewImpl.getCaseFiling("non-existent");			
	}
	
	@Test
	public void rejectFilingTest()
	{
		{
			GajeCaseFiling cf = gajeCaseFilingService.find(generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.QUEUED_FOR_IMPORT));
			
			int caseRecordHistorySize = cf.getCaseRecord().getCaseRecordHistory().size();
			
			clerkReviewImpl.rejectFiling(cf.getInternalId(),"Test rejection reason");
			
			cf = gajeCaseFilingService.find(cf.getInternalId());
			
			assertEquals(null,cf.getCaseRecord().getCaseTrackingId()); // the docket number should not be set
			assertEquals("courtUuidTesting",cf.getCaseRecord().getCourt().getUuid()); //court uuid should be test
			assertEquals(caseRecordHistorySize + 1,cf.getCaseRecord().getCaseRecordHistory().size()); // history should be increased by 1
			assertEquals("Test rejection reason",cf.getCaseRecord().getRejectionReason());
			
			{ //check history			
				assertEquals(GajeConstants.CaseRecordEventType.FILING_REJECTED,
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getEventType().getCode() );
				assertEquals(cf.getCaseRecord().getUuid(),
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getCaseRecord().getUuid());
				assertEquals(null,
						((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getPerson() );
				//assertEquals("Test rejection reason",
				//		((CaseRecordHistory)cf.getCaseRecord().getCaseRecordHistory().toArray()[caseRecordHistorySize]).getComment() );
			}
			
			{// check court date 
				assertEquals(null,cf.getCaseRecord().getCourtDate());
			}			
		}
	}
	
	@Test(expected=ObjectNotFound.class)
	public void rejectFilingOfNonExistentTest()
	{
		clerkReviewImpl.rejectFiling("non-existent","Test rejection reason");		
	}
	
	@Test(expected=ObjectNotFound.class)
	public void rejectFilingOfANonQueuedCase()
	{
		{
			GajeCaseFiling cf = gajeCaseFilingService.find(generateFiling("courtUuidTesting",GajeConstants.CaseRecordStatus.IMPORTED));
			clerkReviewImpl.rejectFiling(cf.getInternalId(),"Test rejection reason");		
		}	
	}
	
}
