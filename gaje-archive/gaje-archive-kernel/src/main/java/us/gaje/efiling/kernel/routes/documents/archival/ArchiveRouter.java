package us.gaje.efiling.kernel.routes.documents.archival;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.xml.Namespaces;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.kernel.BaseKernel;
import us.gaje.efiling.kernel.logic.documents.ArchiveDocumentBinary;
import us.gaje.efiling.kernel.logic.documents.GetImportedDocuments;
import us.gaje.efiling.kernel.logic.documents.GetOlderDocumentVersions;
import us.gaje.efiling.kernel.logic.documents.IdentitySplitter;
import us.gaje.efiling.kernel.logic.documents.ReportDocumentCount;
import us.gaje.efiling.model.documents.Document;
import us.gaje.efiling.model.documents.DocumentVersion;

/**
 * Router which sets up a system for archiving old document versions.
 * 
 * @author artripa
 * 
 */
@Component
public class ArchiveRouter extends BaseKernel {

	@Autowired
	private DaoManager daoManager;
	@Autowired
	private ArchiveDocumentBinary archiveDocumentBinary;
	@Autowired
	private GetImportedDocuments getImportedDocuments;
	@Autowired
	private GetOlderDocumentVersions getOlderDocumentVersions;
	
	@Autowired
	private ReportDocumentCount reportDocumentCount;
	
	
	@Transactional	
	@Override
	public void configure() throws Exception {

		onException(Exception.class).to("activemq:" + ArchiveRouterConstants.ARCHIVE_FAULT_QUEUE);

		Namespaces archiveNamespace = new Namespaces("ar",
				"http://www.gaje.us/schemas/archive");

		
		{
			/**
			 * Setup for archiving of document binaries which are:
			 * 	1) Imported
			 *  2) NOT the most recent version
			 */
			from("activemq:" + ArchiveRouterConstants.START_OLD_REVISIONS_PROCESS_QUEUE)
				.inOnly()	
				.setBody(simple(""))
				.setHeader("documentSize").method(getImportedDocuments,"getImportedDocumentCount")
				.setHeader("documentStepsize",constant(3000))
				.setHeader("loopCount").groovy("(int) Math.ceil(request.headers.documentSize / request.headers.documentStepsize)")
				.setHeader("size").header("documentStepsize")
				.loop(header("loopCount"))
					.setBody(simple(""))
					.setHeader("start").groovy("request.headers.documentStepsize * exchange.properties.CamelLoopIndex")					
					.transform().method(getImportedDocuments, "getImportedDocuments")
					.split().method(new IdentitySplitter<Object>())
						.to("direct:" + ArchiveRouterConstants.ARCHIVE_PREVIOUS_REVISIONS_QUEUE)
					.end()
					.bean(reportDocumentCount)
				.end();										
		}
		
		{
			
			
			
			/**
			 * Setup for archiving of document binaries which are:
			 * 	1) Imported
			 *  2) Older than the given months
			 */
			from("activemq:" + ArchiveRouterConstants.START_BY_DATE_PROCESS_QUEUE)
				.inOnly()	
				.setHeader(ArchiveRouterConstants.DATE_HEADER,body())
				.setBody(simple(""))
				.setHeader("documentSize").method(getImportedDocuments,"getImportedDocumentsByDateCount")
				.setHeader("documentStepsize",constant(3000))
				.setHeader("loopCount").groovy("(int) Math.ceil(request.headers.documentSize / request.headers.documentStepsize)")
				.setHeader("size").header("documentStepsize")			
				.loop(header("loopCount"))
					.setBody(simple(""))
					.setHeader("start").groovy("request.headers.documentStepsize * exchange.properties.CamelLoopIndex")					
					.transform().method(getImportedDocuments, "getImportedDocumentsByDate")
					.split().method(new IdentitySplitter<Object>())
						.to("direct:" + ArchiveRouterConstants.ARCHIVE_WHOLE_DOCUMENT_QUEUE)
					.end()										
					.bean(reportDocumentCount)
				.end();										
		}
		
		
		{

			/**
			 * Archives the binaries of a document that aren't the currently-used
			 * binary.
			 */
			from("activemq:" + ArchiveRouterConstants.ARCHIVE_PREVIOUS_REVISIONS_QUEUE)
					.inOnly()
					.throttle(40)
					.to("direct:" + ArchiveRouterConstants.ARCHIVE_PREVIOUS_REVISIONS_QUEUE);
				
			from("direct:" + ArchiveRouterConstants.ARCHIVE_PREVIOUS_REVISIONS_QUEUE)
					.log("BodyA: ${body}")
					.wireTap("direct:processDocCount")
					.transform().method(daoManager.get(Document.class), "read")
					.log("BodyB: ${body}")
					.transform().method(getOlderDocumentVersions).split()
					.method(new IdentitySplitter<Object>())
					.log("BodyC: ${body}")
					.to("direct:" + ArchiveRouterConstants.ARCHIVE_DOCUMENT_BINARY_QUEUE);
			
			from("direct:processDocCount")
				.bean(reportDocumentCount,"processStartEvent");			
		}

		{

			/**
			 * Setup for archiving of document binaries which are: 
			 * 1) IMPORTED
			 * 2) Older than X months, regardless of version.
			 * 
			 * 
			 * Steps: 1) parse input, add to header 2) transform input to list
			 * of document uuids. 3) for each document UUID, send it to queue by
			 * date.
			 *
			from("direct:" + ArchiveRouterConstants.START_BY_DATE_PROCESS_QUEUE)
					.setHeader(
							ArchiveRouterConstants.COURT_ID_HEADER,
							xpath(
									"/ar:ArchiveOldDocumentsByDate/ar:court/text()")
									.namespaces(archiveNamespace))
					.setHeader(
							ArchiveRouterConstants.ARCHIVE_BEFORE_DATE_HEADER,
							xpath(
									"/ar:ArchiveOldDocumentsByDate/ar:archiveBefore")
									.namespaces(archiveNamespace)).transform()
					.method(getImportedDocuments, "getImportedDocumentsByDate")
					.split().method(new IdentitySplitter<Object>())
					.to("activemq:" + ArchiveRouterConstants.ARCHIVE_WHOLE_DOCUMENT_QUEUE);
					*/
		}
		

		{

			/**
			 * Setup for the general use case of archiving a document and all of it's binaries
			 */
			from("activemq:" + ArchiveRouterConstants.ARCHIVE_WHOLE_DOCUMENT_QUEUE)
					.inOnly()
					.throttle(40)
					.to("direct:" + ArchiveRouterConstants.ARCHIVE_WHOLE_DOCUMENT_QUEUE);

			from("direct:" + ArchiveRouterConstants.ARCHIVE_WHOLE_DOCUMENT_QUEUE)
				.transform().method(daoManager.get(Document.class), "read")		// read in the entity
				.transform().simple("${body.documentVersions}")					// pull out each version
				.split().method(new IdentitySplitter<Object>())					// for each version, 
					.transform().simple("${body.uuid}")							//		get the UUID
					.to("direct:" + ArchiveRouterConstants.ARCHIVE_DOCUMENT_BINARY_QUEUE); // and send UUID to QUEUE
			
			
		}
		
		{
			/**
			 * Setup for the general use case of archiving a single document binary.
			 */
			from("activemq:" + ArchiveRouterConstants.ARCHIVE_DOCUMENT_BINARY_QUEUE)
					.inOnly()
					.throttle(40)
					.to("direct:" + ArchiveRouterConstants.ARCHIVE_DOCUMENT_BINARY_QUEUE);
			
			from("direct:" + ArchiveRouterConstants.ARCHIVE_DOCUMENT_BINARY_QUEUE)			
					// .choice()
					// .when(isFultonCounty())
					// .process(archiveDocumentBinaryOverSSH)
					// .otherwise()
					.transform().method(daoManager.get(DocumentVersion.class), "read")
					.bean(archiveDocumentBinary);
		}
		
	}

	public void setArchiveDocumentBinary(
			ArchiveDocumentBinary archiveDocumentBinary) {
		this.archiveDocumentBinary = archiveDocumentBinary;
	}

	public void setDaoManager(DaoManager daoManager) {
		this.daoManager = daoManager;
	}

	@Override
	public DaoManager getDaoManager() {
		return daoManager;
	}

}
