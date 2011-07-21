package us.gaje.service.exceptions;

/**
 * Generic exception for Service Layer thrown when the requested object can not be found.
 * @author artripa
 */
public class ObjectNotFound extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ObjectNotFound(String format) {
		super(format);
	}

}
