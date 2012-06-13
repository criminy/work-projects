package us.gaje.efiling.kernel.logic.documents;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("singleton")
public class ReportDocumentCount {

	private int archived = 0;
	private int bprocessed = 0;
	private int processed = 0;
	
	public void archiveStartEvent()
	{
		bprocessed++;
	}
	
	public void archiveSuccessfulEvent()
	{
		archived++;
	}
	
	public void processStartEvent()
	{
		processed++;
	}
	
	private int ioerrors = 0;
		
	@Handler
	public void reportCount(@Header("documentSize") int totalDocuments)
	{		
		System.out.println(String.format(
				"Total Documents: %d      Processed: %d     Successfully Archived Binaries: %d/%d   IOErrors: %d ",
				totalDocuments,processed,archived,bprocessed,ioerrors));
				
	}

	public void archiveFailedEvent(String string) {		
		ioerrors++;
	}
	
}
