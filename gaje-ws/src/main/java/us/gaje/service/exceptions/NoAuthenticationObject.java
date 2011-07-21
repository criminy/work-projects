package us.gaje.service.exceptions;

public class NoAuthenticationObject extends Exception {

	private static final long serialVersionUID = 1L;

	public NoAuthenticationObject(String format) {
		super(format);
	}

}
