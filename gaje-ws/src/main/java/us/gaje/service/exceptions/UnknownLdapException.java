package us.gaje.service.exceptions;

public class UnknownLdapException extends UnknownException {
	private static final long serialVersionUID = 1L;

	public UnknownLdapException(String format) {
		super(format);
	}
	public UnknownLdapException(String format,Exception exn) {
		super(format,exn);
	}
}
