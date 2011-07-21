package us.gaje.efiling.ws.clerkreview.util;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaaoc.gajews.types.DocumentList;
import us.gaaoc.gajews.types.NewDocumentType;
import us.gaaoc.gajews.types.UuidList;
import us.gaje.efiling.ws.clerkreview.ClerkReviewDelegatingProxy;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


/**
 * Unit tests which verify the ProxyBuilder class
 * @author artripa
 *
 */
public class ProxyBuilderTest {

	/**
	 * Tests the order of operations on a chain of proxies and the resulting implementation.
	 */
	@Test
	public void testOrderOfOperation()
	{
		final StringBuffer buf = new StringBuffer();
		ClerkReviewStringBufferAppendingTestProxy proxy1 = new ClerkReviewStringBufferAppendingTestProxy(buf,"1");
		ClerkReviewStringBufferAppendingTestProxy proxy2 = new ClerkReviewStringBufferAppendingTestProxy(buf,"2");
				
		ClerkReview impl = mock(ClerkReview.class);
	
		when(impl.getQueuedFilings("")).thenAnswer(new Answer() {
			public Answer answer(InvocationOnMock invocation) throws Throwable {
				buf.append("getQueuedFilings");
				return null;
			}
		});
		
		when(impl.getCaseFiling("")).thenAnswer(new Answer() {
			public Answer answer(InvocationOnMock invocation) throws Throwable {
				buf.append("getCaseFiling");
				return null;
			}
		});
		
		ProxyBuilder proxy = new ProxyBuilder();
		
		ClerkReview rev = proxy.build(ClerkReview.class)
			.proxy(proxy1)
			.proxy(proxy2)
			.impl(impl);
		
		rev.getQueuedFilings("");
		assertEquals("12getQueuedFilings",buf.toString());
		
		verify(impl).getQueuedFilings("");
		
		rev.getCaseFiling("");
		assertEquals("12getQueuedFilings12getCaseFiling",buf.toString());
		
		
		verify(impl).getCaseFiling("");
	}
	
	
}

/**
 * Proxy which appends to a stringbuffer on the calls.
 * 
 * @author artripa
 *
 */
class ClerkReviewStringBufferAppendingTestProxy extends ClerkReviewDelegatingProxy implements ClerkReview
{
	StringBuffer x;
	String s;
	public ClerkReviewStringBufferAppendingTestProxy(StringBuffer x,String s) {
		this.x = x;
		this.s = s;
	}
	
	@Override
	public UuidList getQueuedFilings(String courtID) {
		x.append(s);
		return super.getQueuedFilings(courtID);
	}
	
	@Override
	public CaseFiling getCaseFiling(String uuid) {
		x.append(s);
		return super.getCaseFiling(uuid);
	}
	
	
	@Override
	public void acceptFiling(String uuid, String docket, String judge,
			XMLGregorianCalendar courtDate, DocumentList documents) {
		x.append(s);
		super.acceptFiling(uuid, docket, judge, courtDate, documents);
	}
	
	@Override
	public String addDocument(String uuid, NewDocumentType document) {
		x.append(s);
		return super.addDocument(uuid, document);
	}
	
	@Override
	public void rejectFiling(String uuid, String rejectionReason) {
		x.append(s);
		super.rejectFiling(uuid, rejectionReason);
	}	
}

