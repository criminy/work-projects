package us.gaje.service;

import us.gaje.model.stamping.CourtStampMetaData;

/**
 * 
 * This is a class for determining offsets and text for
 * the court stamp.
 * 
 * @author artripa
 *
 */
public interface CourtStampInformation {
	
	/**
	 * Returns the information for a court stamp.
	 * @param courtUuid The court to query
	 * @param x_stamp The initial X position
	 * @param y_stamp The initial Y position
	 * @return The translated position
	 */
	public CourtStampMetaData create(String courtUuid,float x_stamp,float y_stamp);
}
