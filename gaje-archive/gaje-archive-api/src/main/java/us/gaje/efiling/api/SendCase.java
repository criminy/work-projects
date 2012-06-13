package us.gaje.efiling.api;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Object which represents a Sending of a case 
 * from one module to another.
 * @author artripa
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class SendCase extends UserEvent{

	private static final long serialVersionUID = 1L;

}
