package us.gaje.efiling.model.enums;

/**
 * Enumeration of valid role codes in the efiling system, grabbed
 * from the SQL database.
 * http://www.vineetmanohar.com/2010/01/3-ways-to-serialize-java-enums/
 * @author artripa
 *
 */
public enum Roles {
	ATTORNEY(1,"Attorney"),
	CLERK(2,"Clerk"),
	JUDGE(3,"Judge"),
	AGENT(4,"Agent")
	;
	public static Roles fromValue(int val)
	{
		for( Roles ds : values() ){
			if(ds.val == val)
				return ds;
		}
		return null;		
	}
	
	private Roles(int val,String desc) {
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
