package us.gaje.efiling.ws.clerkreview;

import javax.xml.datatype.XMLGregorianCalendar;

import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaaoc.gajews.types.DocumentList;
import us.gaaoc.gajews.types.NewDocumentType;
import us.gaaoc.gajews.types.UuidList;
import us.gaje.efiling.ws.clerkreview.util.Chainable;

/**
 * Proxy class which allows the extending class to wrap the webservice
 * methods around utility code.
 * 
 * Note: There is a weird design that will not allow us to make this class
 * an implementation of ClerkReview and still use it as a spring bean
 * to be injected into the CXF framework. The problem is in the
 * CXF framework.
 * 
 * @author artripa
 *
 */
public class ClerkReviewDelegatingProxy implements Chainable<ClerkReview>
{

	private ClerkReview next;

	public void setNext(ClerkReview next) {
		this.next = next;
	}
	
		
	public UuidList getQueuedFilings(String courtID) {		
		return next.getQueuedFilings(courtID);
	}

	public CaseFiling getCaseFiling(String uuid) {
		return next.getCaseFiling(uuid);
	}

	public void rejectFiling(String uuid, String rejectionReason) {
		next.rejectFiling(uuid,rejectionReason);
	}
	
	public void acceptFiling(String uuid, String docket, String judge,
			XMLGregorianCalendar courtDate, DocumentList documents) {
		next.acceptFiling(uuid,docket,judge,courtDate,documents);
	}

	public String addDocument(String uuid, NewDocumentType document)  {
		return next.addDocument(uuid,document);
	}
	

}
