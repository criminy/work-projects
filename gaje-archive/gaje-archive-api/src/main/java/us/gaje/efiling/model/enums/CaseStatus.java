package us.gaje.efiling.model.enums;

/**
 * Enumeration of valid case status codes in the efiling system, grabbed
 * from the SQL database.
 * http://www.vineetmanohar.com/2010/01/3-ways-to-serialize-java-enums/
 * @author artripa
 *
 */
public enum CaseStatus {
	Received(1,"Received"),
	Imported(2,"Imported"),
	Rejected(3,"Rejected"),
	Served(4,"Served"),
	NonServed(5,"Non-Served"),
	Disposed(6,"Disposed"),
	Unsent(7,"Unsent"),
	rejectedByAttorney(8,"Rejected By Attorney"),
	ReceivedByAttorney(10,"Received By Attorney"),
	ReadyToSendToCourt(11,"Ready To Send To Court"),
	Filed(12,"Filed"),
	ReceivedByJudge(13,"Received By Judge"),
	ReceivedByClerk(14,"Received By Clerk"),
	UnsentAddition(15,"Unset Addition"),
	SentToAttorneyAddition(16,"Sent To Attorney Addition"),
	ReadyToDispose(17,"Ready To Dispose"),
	ReceivedFromJudge(18,"Received From Judge"),
	RejectedByJudge(19,"Rejected By Judge"),
	ReadyToImport(20,"Ready To Import"),
	QueuedForImport(21,"Queued For Import"),
	FailedToImport(22,"Failed To Import"),
	
	
	/*
	 * There is one status PER module 
	 */
	AGENT(40,"Agent"),
	ATTORNEY(41,"Attorney"),
	CLERK(42,"Clerk"),
	JUDGE(43,"Judge"),
	
	/*
	 * And there are status codes to support the webservice modules.
	 */
	QUEUED_FOR_IMPORT(21,"Queued For Import"),
	IMPORTED(2,"Imported")
	;	
	
	public static CaseStatus fromValue(int val)
	{
		for( CaseStatus ds : values() ){
			if(ds.val == val)
				return ds;
		}
		return null;		
	}
	
	private CaseStatus(int val,String desc) {
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
