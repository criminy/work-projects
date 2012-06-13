package us.gaje.efiling.kernel.logic.documents;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Handler;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.kernel.routes.documents.archival.ArchiveRouter;
import us.gaje.efiling.kernel.routes.documents.archival.ArchiveRouterConstants;
import us.gaje.efiling.model.documents.Document;
import us.gaje.efiling.model.documents.DocumentVersion;

@Component
public class GetOlderDocumentVersions {
		
	@Handler
	@Transactional
	public List<String> getOlderVersions(Document d)
	{				
		List<String> results = new ArrayList<String>();
	
		if(d == null){
			throw new RuntimeException("Document is null!");
		}
	
		if(d.getDocumentVersions() == null) {
			throw new RuntimeException("d.getDocumentVersions is null!");
		}
		int maxV = 0;
		for(DocumentVersion dv : d.getDocumentVersions())
		{
			if(dv.getVersion() > maxV)
			{
				maxV = dv.getVersion();
			}
		}
				
		for(DocumentVersion dv : d.getDocumentVersions())
		{
			if(maxV > dv.getVersion())
			{				
				//TODO: logic about whether to include specific documents based on pathnames should
				// not be in this file, UNLESS we rename it "GetOlderDocumentVersionsNotArchived"
				if(!dv.getPath().startsWith(ArchiveRouterConstants.ARCHIVE_LOCATION))
					results.add(dv.getUuid());					
			}
		}	
		return results;
	}
	
}
