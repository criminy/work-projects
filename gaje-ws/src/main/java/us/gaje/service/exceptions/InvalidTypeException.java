package us.gaje.service.exceptions;

public class InvalidTypeException extends Exception {
	private static final long serialVersionUID = 1L;

	public InvalidTypeException(String format) {
		super(format);
	}
}
