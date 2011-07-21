package us.gaje.efiling.ws.clerkreview;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

import us.gaaoc.framework.model.CaseRecord;
import us.gaaoc.framework.model.Court;
import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.DocumentList;
import us.gaje.service.GajeCaseFilingService;


public class ClerkReviewLoggingProxyTest {
	
	ClerkReview clerkReview;
	GajeCaseFilingService gajeCaseFilingService;
	GajeCaseFiling gajeCaseFiling;
	CaseRecord cr;
	Court court;
	ClerkReviewLoggingProxy loggingProxy;
	
	String uuid = "ExampleCaseRecordUuid";
	String courtUuid = "courtUuidMock";
	
	@Before
	public void setup()
	{
		clerkReview = mock(ClerkReview.class);
		gajeCaseFilingService = mock(GajeCaseFilingService.class);
		gajeCaseFiling = mock(GajeCaseFiling.class);
		cr = mock(CaseRecord.class);
		court = mock(Court.class);

		when(gajeCaseFilingService.find(uuid)).thenReturn(gajeCaseFiling);
		when(gajeCaseFiling.getCaseRecord()).thenReturn(cr);
		when(cr.getCourt()).thenReturn(court);
		when(court.getUuid()).thenReturn(courtUuid);

		loggingProxy = new ClerkReviewLoggingProxy();
		
		loggingProxy.setNext(clerkReview);
		loggingProxy.setGajeCaseFilingService(gajeCaseFilingService);
		loggingProxy.setLevel("testing");
	}
	
	@Test
	public void testGetCaseFiling() {						
		loggingProxy.getCaseFiling(uuid);		
		verify(clerkReview).getCaseFiling(uuid);
	}
	
	@Test
	public void testAcceptFiling()
	{
		DocumentList dl = new DocumentList();		
		loggingProxy.acceptFiling(uuid,"01TEST01", null, null,dl);		
		verify(clerkReview).acceptFiling(uuid,"01TEST01", null, null,dl);
	}
	
	@Test
	public void testRejectFiling() {
		loggingProxy.rejectFiling(uuid,"this is a reject reason");
		verify(clerkReview).rejectFiling(uuid,"this is a reject reason");
	}
	
}
