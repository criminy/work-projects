package us.gaje.service.exceptions;

public class UnknownFatalException extends UnknownException {
	private static final long serialVersionUID = 1L;

	public UnknownFatalException(String format) {
		super(format);
	}
	public UnknownFatalException(String format,Exception exn) {
		super(format,exn);
	}
}
