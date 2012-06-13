package us.gaje.efiling.kernel.logic.documents;

import java.util.List;

import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.model.documents.Document;
import us.gaje.efiling.model.documents.DocumentVersion;


/**
 * Processor which archives every document by throttling againsts a 
 * given step size, so that 
 * @author artripa
 *
 */
@Component
public class ThrottlingDocumentArchiver {

	@Autowired private ArchiveDocumentBinary archiveDocumentBinary;
	@Autowired private GetImportedDocuments getImportedDocuments;
	
	@Handler
	@Transactional
	public void processDocument(@Header("stepsize") int stepSize)
	{	
		int totalSize = getImportedDocuments.getImportedDocumentCount(null);
	
		for(int x = 0; x<=totalSize; x+=stepSize)
		{
			List<String> doc = getImportedDocuments.getImportedDocuments(null, x,stepSize);
		}
		
		
	}
	
}

