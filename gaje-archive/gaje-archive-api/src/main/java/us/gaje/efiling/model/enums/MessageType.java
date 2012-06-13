package us.gaje.efiling.model.enums;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of valid EventType codes in the efiling system, grabbed
 * from the SQL database.
 * http://www.vineetmanohar.com/2010/01/3-ways-to-serialize-java-enums/
 * @author artripa
 *
 */
public enum MessageType {
	IMPORTED(1,"Imported"),
	REJECTED(2,"Rejected"),
	RECEIVED(3,"Received"),
	SENT(4,"Sent"),
	System(5,"System")
	
	;
	
	
	public static Map<Integer,MessageType> messageTypes;
	static {
		messageTypes = new HashMap<Integer, MessageType>();
		for(MessageType mt : MessageType.values())
		{
			messageTypes.put(mt.getValue(), mt);
		}
		messageTypes = Collections.unmodifiableMap(Collections.synchronizedMap(messageTypes));
	}
	
	public static MessageType fromValue(int val)
	{
		return messageTypes.get(val);
	}
	
	private MessageType(int val,String desc) {
		this.val = val;
		this.description = desc;
	}
	
	private String description;//TODO: load descriptions from i18n subsystem
	private final int val;
	
	
	public int getValue()
	{
		return this.val;
	}
	
	public String getDescription()
	{
		return this.description;
	}
	
	public String toString() {
		
		return this.getDescription();
	};
}
