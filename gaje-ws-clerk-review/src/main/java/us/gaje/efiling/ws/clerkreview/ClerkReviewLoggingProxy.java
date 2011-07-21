package us.gaje.efiling.ws.clerkreview;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

import us.gaaoc.framework.model.Court;
import us.gaaoc.gajews.ClerkReview;
import us.gaaoc.gajews.types.CaseFiling;
import us.gaaoc.gajews.types.DocumentList;
import us.gaaoc.gajews.types.NewDocumentType;
import us.gaje.service.GajeCaseFilingService;

/**
 * Implementation of clerkReview which uses log4j
 * to log information about each request.
 * 
 * @see ClerkReview
 * @see ClerkReviewDelegatingProxy
 * @author artripa
 *
 */
public class ClerkReviewLoggingProxy extends ClerkReviewDelegatingProxy implements ClerkReview{


	Logger log = Logger.getLogger(ClerkReviewLoggingProxy.class);
	
	private GajeCaseFilingService gajeCaseFilingService;
	private Logger rejectlog = Logger.getLogger("rejectlog");
	private Logger methodLog = Logger.getLogger("methodLog");
	
	private String level;
		
	@Override
	public String addDocument(String uuid, NewDocumentType document) {
		Court court = gajeCaseFilingService.find(uuid).getCaseRecord().getCourt();
		
		methodLog.info(String.format(
			"addDocument { level: '%s', document: '%s', uuid: '%s', court: '%s' }",
			level, document.getTitle().substring(0,20), uuid,court.getUuid()));
		
		return super.addDocument(uuid, document);
	}
	
	@Transactional
	@Override
	public CaseFiling getCaseFiling(String uuid) {
		Court court = gajeCaseFilingService.find(uuid).getCaseRecord().getCourt();
		
		methodLog.info(String.format(
			"getCaseFiling { level: '%s', uuid: '%s', court: '%s' }",
			level, uuid,court.getUuid()));
		return super.getCaseFiling(uuid);
	}
	
	@Transactional
	@Override
	public void acceptFiling(String uuid, String docket, String judge,
			XMLGregorianCalendar courtDate, DocumentList documents) {
		
		Court court = gajeCaseFilingService.find(uuid).getCaseRecord().getCourt();
		
		methodLog.info(String.format(
			"acceptFiling { level: '%s', uuid: '%s', docket: '%s', judge: '%s', court: '%s' }",
			level, uuid,docket,judge,court.getUuid()));
	
		super.acceptFiling(uuid, docket, judge, courtDate, documents);		
	}
	
	@Transactional
	public void rejectFiling(String uuid, String rejectionReason) {
		GajeCaseFiling gcf = this.gajeCaseFilingService.find(uuid);
		//log the reason.
		rejectlog.info("Case was rejected by the court: " +
					"<br/>Reason: " + rejectionReason + 
					"<br/>Court: " + gcf.getCaseRecord().getCourtUuid() + "" +
					"<br/>UUID: " + gcf.getCaseRecord().getUuid() + 
					"<br/>Caption: " + gcf.getCaseRecord().getCaseCaption());
		
		methodLog.info(String.format(
			"rejectFiling { level: '%s', uuid: '%s', rejectReason: '%s', court: '%s' }",
			level,uuid,rejectionReason.substring(0,20),gcf.getCaseRecord().getCourt().getUuid()));
		
		
		super.rejectFiling(uuid,rejectionReason);
	}
	
	public void setGajeCaseFilingService(
			GajeCaseFilingService gajeCaseFilingService) {
		this.gajeCaseFilingService = gajeCaseFilingService;
	}
	
	public void setLevel(String level) {
		this.level = level;
	}
	
	public void setRejectlog(Logger rejectlog) {
		this.rejectlog = rejectlog;
	}
	
	public void setMethodLog(Logger methodLog) {
		this.methodLog = methodLog;
	}
}
