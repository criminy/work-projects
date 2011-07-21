package us.gaje.service;

import java.util.List;

import us.gaje.model.Vendor;

/**
 * Service for interfacing with Vendor objects.
 * 
 * @author artripa
 *
 */
public interface VendorService {
	public List<Vendor> find(String _courtID, String _user);
}
