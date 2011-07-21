package us.gaje.service;

import java.util.List;
import us.gaje.efiling.ws.clerkreview.GajeCaseFiling;

/**
 * Service for finding case filings.
 * 
 * @author artripa
 *
 */
public interface GajeCaseFilingService {

	/**
	 * Find the GajeCaseFiling 
	 * @param id The internal ID of the case filing.
	 * @return The GajeCaseFiling object.
	 * @throws ObjectNotFound
	 */
	public GajeCaseFiling find(String id);
	
	/**
	 * Get a list of GajeCaseFiling objects given the courtId and the
	 * status of the cases.
	 * 
	 * @param courtId The ID of the court.
	 * @param status The status of the cases to search.
	 * @return The list of GajeCaseFiling objects.
	 */
	public List<GajeCaseFiling> byCourtIdAndStatus(String courtId,int status);
		
}
