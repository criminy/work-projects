package us.gaje.efiling.api;

import java.io.Serializable;

/**
 * Object sent when the sending of a case has failed.
 * @author artripa
 *
 */
public class SendingFailed implements Serializable{

	private static final long serialVersionUID = 1L;
	private String message;
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	public SendingFailed(String msg) {
		this.message = msg;
	}
	public SendingFailed() {
	}
	
}
