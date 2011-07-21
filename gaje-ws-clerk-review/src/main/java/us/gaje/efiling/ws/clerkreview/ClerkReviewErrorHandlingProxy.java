package us.gaje.efiling.ws.clerkreview;

import java.util.UUID;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaaoc.gajews.types.DocumentList;
import us.gaaoc.gajews.types.NewDocumentType;
import us.gaaoc.gajews.types.UuidList;
import us.gaje.service.exceptions.ObjectNotFound;
import us.gaje.service.web.SessionService;

/**
 * Clerk review implementation 
 * which catches errors, reports them,
 * and re-throws them.
 * 
 * @author artripa
 *
 */
public class ClerkReviewErrorHandlingProxy extends ClerkReviewDelegatingProxy implements ClerkReview {
	

	private SessionService sessionService;
	private Logger log = Logger.getLogger(ClerkReviewErrorHandlingProxy.class);

	
	
	protected String generateErrorCodeId(){
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	protected RuntimeException handleError(String method,Throwable thr){
		String errorCode = generateErrorCodeId();
		log.error("Error: " + method,thr);
		log.error("      Error tracking id: " + errorCode);
		log.error("      Username : " + sessionService.getUsername());
		
		return new RuntimeException("Error in " + method + " with tracking id " + errorCode);
	}
	
	public void acceptFiling(String uuid, String docket, String judge,
			XMLGregorianCalendar courtDate, DocumentList documents) {

		try {
			super.acceptFiling(uuid, docket, judge, courtDate, documents);
		}catch(ObjectNotFound exn)
		{
			throw new RuntimeException(exn.getMessage(),exn);
		}catch(Throwable thr)
		{
			RuntimeException t = handleError("acceptFiling",thr);
			log.error("     uuid=" + uuid);
			log.error("     docket=" + docket);
			log.error("     judge=" + judge);
			log.error("     courtDate=" + courtDate);
			log.error("     documents.size=" + documents.getDocument().size());			
			throw t;			
		}
	}

	public UuidList getQueuedFilings(String courtID) {
		try {
			return super.getQueuedFilings(courtID);
		}catch(ObjectNotFound exn)
		{
			throw new RuntimeException(exn.getMessage(),exn);
		}catch(Throwable thr)
		{
			RuntimeException t = handleError("getQueuedFilings",thr);
			log.error("     courtID=" + courtID);
			throw t;			
		}
	}

	public CaseFiling getCaseFiling(String uuid) {
		try {
			return super.getCaseFiling(uuid);
		}catch(ObjectNotFound exn)
		{
			throw new RuntimeException(exn.getMessage(),exn);
		}catch(Throwable thr)
		{
			RuntimeException t = handleError("getCaseFiling",thr);
			log.error("     uuid=" + uuid);
			throw t;			
		}
	}

	@Transactional
	public void rejectFiling(String uuid, String rejectionReason) {
		try {
			super.rejectFiling(uuid,rejectionReason);
		}catch(ObjectNotFound exn)
		{
			throw new RuntimeException(exn.getMessage(),exn);
		}catch(Throwable thr)
		{
			RuntimeException t = handleError("rejectFiling",thr);
			log.error("     uuid=" + uuid);
			throw t;			
		}
	}
	
	public String addDocument(String uuid, NewDocumentType document) {
		try {
			return super.addDocument(uuid,document);
		}catch(ObjectNotFound exn)
		{
			throw new RuntimeException(exn.getMessage(),exn);
		}catch(Throwable thr)
		{
			RuntimeException t = handleError("addDocument",thr);
			log.error("     uuid=" + uuid);
			log.error("     document.title=" + document.getTitle());
			log.error("     document.type=" + document.getType());
			log.error("     document.content.size=" + document.getContent().length);
			throw t;			
		}
	}
	
	
	public void setSessionService(SessionService sessionService) {
		this.sessionService = sessionService;
	}
}
