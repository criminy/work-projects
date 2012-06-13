package us.gaje.efiling.model.enums;

/**
 * Enumeration of valid EventType codes in the efiling system, grabbed
 * from the SQL database.
 * http://www.vineetmanohar.com/2010/01/3-ways-to-serialize-java-enums/
 * @author artripa
 *
 */
public enum EventType {
	FilingReceivedbyClerk(1,"Filing Received by Clerk"),
	FilingImportedtoCMS(2,"Filing Imported to CMS"),
	FilingRejected(3,"Filing Rejected"),
	Served(4,"Served"),
	NonServed(5,"Non - Served"),
	FilingDisposed(6,"Filing Disposed"),
	Error(7,"Error"),
	FilingRejectedbyAttorney(8,"Filing Rejected by Attorney"),
	FilingReceivedbyAttorney(9,"Filing Received by Attorney"),
	FilingSignedbyAttorney(10,"Filing Signed by Attorney"),
	FilingReceivedbyJudge(11,"Filing Received by Judge"),
	FilingCreatedbyLegalPreparer(12,"Filing Created by Legal preparer"),
	FilingReadiedforaddition(13,"Filing Readied for addition"),
	FilingReadytoDispose(14,"Filing Ready to Dispose"),
	FilingReviewedandSignedbyJudge(15,"Filing Reviewed and Signed by Judge"),
	FilingRejectedbyJudge(16,"Filing Rejected by Judge"),
	FilingReviewedandSignedbyClerk(17,"Filing Reviewed and Signed by Clerk"),
	FilingNotAcceptedByCourtduetoTechnicalError(18,"Filing Not Accepted By Court due to Technical Error"),
	FilingNotAcceptedByCourtduetoClerkSignatureFailure(19,"Filing Not Accepted By Court due to Clerk Signature Failure"),
	FilingNotAcceptedByCourtDuetoMissingCourtReceivedStamps(20,"Filing Not Accepted By Court Due to Missing Court Received Stamps"),
	CourtDateResetbyAttorney(21,"Court Date Reset by Attorney"),
	CourtDateResetbyClerk(22,"Court Date Reset by Clerk"),
	MissingAttorneySignature(23,"Missing Attorney Signature"),
	QueuedForImportByAgent(40,"Queued For Import By Agent");
	
	
	
	
	public static EventType fromValue(int val)
	{
		for( EventType ds : values() ){
			if(ds.val == val)
				return ds;
		}
		return null;		
	}
	
	private EventType(int val,String desc) {
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
