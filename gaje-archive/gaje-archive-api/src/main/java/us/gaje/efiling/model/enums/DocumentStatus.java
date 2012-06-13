package us.gaje.efiling.model.enums;

/**
 * Enumeration of valid document status codes in the efiling
 * system, grabbed from the sql database.
 * 
 * http://www.vineetmanohar.com/2010/01/3-ways-to-serialize-java-enums/
 * @author artripa
 *
 */
public enum DocumentStatus {
	
	received(1,"Received"),
	imported(2,"Imported"),
	error(3,"Error"),
	rejected(4,"Rejected"),
	sealed(5,"Sealed"),
	upload(6,"Upload"),
	preparedForImport(7,"Prepared For Import"),
	signedByClerk(8,"Signed By Clerk"),
	stampedWithCourtReceivedStamp(9,"Stamped With Court Stamp"),
	stampedWithCivilActionNumberStamp(10,"Stamped with CV Stamp"),
	signedByAttorney(11,"Signed By Attorney"),
	failedCourtReceivedStamp(12,"FAIL: Court Stamp"),
	failedCivilActionNumberStamp(13,"FAIL: CV Stamp"),
	failedClerkSignature(14,"FAIL: Clerk Sig"),
	failedAttorneySignature(15,"FAIL: Attorney Sig"),
	queuedForImport(16,"Queued For Import");
	
	public static DocumentStatus fromValue(int val)
	{
		for( DocumentStatus ds : values() ){
			if(ds.val == val)
				return ds;
		}
		return null;		
	}
	
	private DocumentStatus(int val,String desc) {
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
