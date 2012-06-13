package us.gaje.efiling.model.enums;

/**
 * Enumeration of valid county codes in the efiling system, grabbed
 * from the SQL database.
 * http://www.vineetmanohar.com/2010/01/3-ways-to-serialize-java-enums/
 * @author artripa
 *
 */
public enum Counties {
	ChathamCounty(1,"courtUuidChatham"),
	
	;
	public static Counties fromValue(int val)
	{
		for( Counties ds : values() ){
			if(ds.val == val)
				return ds;
		}
		return null;		
	}
	
	private Counties(int val,String desc) {
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
