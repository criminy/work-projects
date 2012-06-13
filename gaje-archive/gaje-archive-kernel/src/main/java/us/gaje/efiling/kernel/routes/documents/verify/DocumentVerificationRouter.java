package us.gaje.efiling.kernel.routes.documents.verify;

import java.io.File;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.kernel.BaseKernel;
import us.gaje.efiling.kernel.logic.documents.ArchiveDocumentBinary;
import us.gaje.efiling.kernel.logic.documents.DocumentVerifyingCounter;
import us.gaje.efiling.kernel.logic.documents.GetAllDocuments;
import us.gaje.efiling.kernel.logic.documents.GetImportedDocuments;
import us.gaje.efiling.kernel.logic.documents.GetOlderDocumentVersions;
import us.gaje.efiling.kernel.logic.documents.GetUnImportedDocuments;
import us.gaje.efiling.kernel.logic.documents.IdentitySplitter;
import us.gaje.efiling.kernel.logic.documents.ReportDocumentCount;
import us.gaje.efiling.kernel.routes.documents.archival.ArchiveRouterConstants;
import us.gaje.efiling.model.documents.Document;
import us.gaje.efiling.model.documents.DocumentVersion;
import us.gaje.efiling.repositories.CaseRepository;
import us.gaje.efiling.repositories.CourtRepository;

/**
 * Router which verifies document PDFs
 * @author artripa
 *
 */
@Component
public class DocumentVerificationRouter  extends BaseKernel {

	@Autowired
	private DaoManager daoManager;
	@Autowired
	private ArchiveDocumentBinary archiveDocumentBinary;
	@Autowired
	private GetAllDocuments getAllDocuments;
	@Autowired
	private GetOlderDocumentVersions getOlderDocumentVersions;
	
	
	@Autowired CaseRepository caseRepository;
	@Autowired CourtRepository courtRepository;
	
	@Autowired
	private ReportDocumentCount reportDocumentCount;
	
	@Autowired
	TransactionalReportingProcessor transactionalReportingProcessor;
	
	@Override
	public void configure() throws Exception {
				
		
		int STEP_SIZE = 9000;

		{
			/**
			 * Setup for verifying of document binaries which are:
			 * 	1) NOT Imported
			 */
			from("activemq:checkDocuments")
				.inOnly()	
				.setBody(simple(""))
				.setHeader("documentSize").method(getAllDocuments,"getAllDocumentsCount")				
				.log("${header.documentSize}")
				.setHeader("documentStepsize",constant(STEP_SIZE))
				.setHeader("loopCount").groovy("(int) Math.ceil(request.headers.documentSize / request.headers.documentStepsize)")
				.setHeader("size").header("documentStepsize")
				.process(new PopulateHeader())
				.loop(header("loopCount"))
					.setBody(simple(""))
					.setHeader("start").groovy("request.headers.documentStepsize * exchange.properties.CamelLoopIndex")
					.log("${header.start}")
					.transform().method(getAllDocuments, "getAllDocuments")
					.split().method(new IdentitySplitter<Object>())
						.to("direct:checkSingleDocument")
					.end()					
				.end()
				.process(transactionalReportingProcessor);
		}
		
		{			
			from("direct:checkSingleDocument")								
				.transform().method(daoManager.get(Document.class), "read")				
				.process(new CounterProcessor());
		}
	}


	@Override
	public DaoManager getDaoManager() {
		return daoManager;
	}
	
}


class PopulateHeader implements Processor{

		@Override
		public void process(Exchange exchange) throws Exception {
			exchange.getIn().setHeader("counter", new DocumentVerifyingCounter());
		}
}

class CounterProcessor implements Processor{
	
	
	public void process(Exchange exchange) throws Exception 
	{
		DocumentVerifyingCounter counter = exchange.getIn().getHeader("counter",DocumentVerifyingCounter.class);
		Document d = exchange.getIn().getBody(Document.class);

		if(d.getCaseRecord().isClearFilingFlag() || d.getCaseRecord().isDeletedFilingFlag()){
			return;
		}
		
		if(d.getContent() != null)
		{
			
		}else{
			counter.addBadDocument(d);		
			return;
		}		
	}	
}

