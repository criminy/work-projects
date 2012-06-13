package us.gaje.efiling.kernel.routes.documents.archival;

public class ArchiveRouterConstants
{
	public static final String ARCHIVE_LOCATION = "/mnt/san";
	
	
	public static final String ARCHIVE_WHOLE_DOCUMENT_QUEUE = "us.gaje.efiling.util.archiveDocument";
	public static final String START_OLD_REVISIONS_PROCESS_QUEUE = "us.gaje.efiling.util.archiveOldDocuments.startProcess";
	public static final String START_BY_DATE_PROCESS_QUEUE = "us.gaje.efiling.util.archiveByDate.startProcess";
	public static final String ARCHIVE_PREVIOUS_REVISIONS_QUEUE = "us.gaje.efiling.util.archiveOldDocuments.documentQueue";
	public static final String ARCHIVE_DOCUMENT_BINARY_QUEUE = "us.gaje.efiling.util.archiveDocumentBinary";
	public static final String ARCHIVE_SUCCESS_QUEUE = "us.gaje.efiling.util.archiveOldDocuments.success";
	public static final String ARCHIVE_FAULT_QUEUE = "us.gaje.efiling.util.archiveOldDocuments.faults";
	
	
	public static final String DATE_HEADER = "GAJE_DATE";
	public static final String COURT_ID_HEADER = "GAJE_COURT_ID";
	public static final String ARCHIVE_BEFORE_DATE_HEADER = "GAJE_ARCHIVE_BEFORE_DATE";
}