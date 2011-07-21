package us.gaje.service.exceptions;

public class UnknownException extends Exception{
	private static final long serialVersionUID = 1L;

	public UnknownException(String format) {
		super(format);
	}
	public UnknownException(String format,Exception exn) {
		super(format,exn);
	}
}
