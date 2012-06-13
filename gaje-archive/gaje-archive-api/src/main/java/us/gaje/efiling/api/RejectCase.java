package us.gaje.efiling.api;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

/**
 * Object which represents rejection of a case 
 * from one module to another.
 * @author artripa
 *
 */
@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class RejectCase extends UserEvent{
	
	private static final long serialVersionUID = 1L;

}
