package us.gaje.service;

import us.gaje.service.exceptions.NoAuthenticationObject;
import us.gaje.service.exceptions.ObjectNotFound;
import us.gaje.service.exceptions.UnknownException;

/**
 * This is a service class which states whether a given entity can 
 * interact with the court.
 * @author artripa
 *
 */
public interface CourtAccessDefinitions {
	/**
	 * Tests whether the given court can be accessed. Pulls the 
	 * authentication tokens from application specific implementations. 
	 * @param courtID The courtID to read/write 
	 * @return True if allowed, false Otherwise
	 * @throws NoAuthenticationObject If there is no authentication token in the session.
	 * @throws ObjectNotFound If the courtID is not found.
	 * @throws UnknownException On an unknown error.
	 */
	public boolean isValidCourtAccess(String courtID);
	
	/**
	 * Tests whether the given court can be accessed by the given user.
	 * @param courtID The courtID to read and write
	 * @param user The user token.
	 * @return True if allowed, false otherwise
	 * @throws NoAuthenticationObject If the user token is invalid
	 * @throws ObjectNotFound If the courtID could not be found.
	 * @throws UnknownException On an unknown error.
	 */
	public boolean isValidCourtAccess(String courtID, String user);
}
