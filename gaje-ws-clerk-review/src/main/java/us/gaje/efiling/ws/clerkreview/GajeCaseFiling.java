package us.gaje.efiling.ws.clerkreview;

import java.util.Date;

import us.gaaoc.framework.model.CaseRecord;
import us.gaaoc.framework.model.DocumentStatus;
import us.gaaoc.framework.model.Documents;
import us.gaaoc.framework.model.Person;
import us.gaaoc.gajews.types.CaseFiling;

/**
 * This is the case filing business object,which 
 * provides a simple abstraction for common business logic actions
 * for the clerk review webservice.
 * 
 * @author artripa
 *
 */
public interface GajeCaseFiling {
	
	/**
	 * Rejects the Case Filing on behalf of the Clerk
	 * @param rejectionReason The reason for rejection.
	 */
	public void rejectByClerk(String rejectionReason);
	
	/**
	 * Accepts the Case Filing on behalf of the Clerk.
	 */
	public void acceptByClerk();
	
	/**
	 * Dockets the Case Filing.
	 * @param docketNumber
	 */	
	public void docket(String docketNumber);
	
	/**
	 * Adds a new event to the case.
	 * @param date The date of the event.
	 * @param p The person who acted on the event (or NULL if no person).
	 * @param historyId The ID of the event. 
	 * @param comment The comment of the event.
	 */
	public void addHistoricalRecord(Date date, Person p,int historyId, String comment);	
	
	/**
	 * Saves the Case Filing to the persistence layer.
	 */
	public void save();
	
	/**
	 * Updates the content of the document with the given UUID.
	 * @param uuid The UUID of the document.
	 * @param content The content of the document.
	 */
	public void updateDocument(String uuid, byte[] content);

	/**
	 * Adds a new document given the content,type, and title
	 * @param title the title of the new document.
	 * @param type the type of the new document.
	 * @param content the byte array of the binary contents of the document.
	 * @return The document object
	 */
	public Documents addDocument(String title, String type, byte[] content,DocumentStatus status);
	
	/**
	 * Sets the currently assigned judge. 
	 * 
	 * @param judge The new judge to assign to this case.
	 */
	public void updateJudge(String judge);
	
	/**
	 * Gets the primary backing object.
	 * @return The primary backing object.
	 */
	public CaseRecord getCaseRecord();
	
	/**
	 * Gets the backing transmission object.
	 * @return The transmission backing object.
	 */
	public CaseFiling getTransmissionCaseFiling();
	
	/**
	 * Sets the primary backing object. 
	 * @param cr The primary backing object.
	 */
	public void setCaseRecord(CaseRecord cr);
	
	/**
	 * Sets the court date.
	 * @param date the court date, which must be in the future.
	 */
	public void setCourtDate(Date date);	
	
	/**
	 * The internal, unique ID
	 * @return The ID as a string.
	 */
	public String getInternalId();
}
