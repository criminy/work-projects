package us.gaje.efiling.kernel.logic.documents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.camel.Body;
import org.apache.camel.CamelContext;
import org.apache.camel.Handler;
import org.apache.camel.ProducerTemplate;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import us.gaje.efiling.kernel.routes.documents.archival.ArchiveRouterConstants;
import us.gaje.efiling.model.documents.DocumentVersion;

/**
 * Logic class for document archival.
 * 
 * @author artripa
 *
 */
@Component
public class ArchiveDocumentBinary {
	@PersistenceContext
	private EntityManager em;

	Logger log = Logger.getLogger(ArchiveDocumentBinary.class);

	@Autowired ReportDocumentCount reportDocumentCount;

	//@Autowired private CamelContext ctx;
	
	/**
	 * Archives a document binary.
	 * @param dv The document version instance.
	 * @throws IOException if there is an error archiving the document.
	 */
	@Handler
	@Transactional
	public void archiveBinary(@Body DocumentVersion dv)
	{			
		reportDocumentCount.archiveStartEvent();
		
		if(dv == null)
		{
			throw new NullPointerException("DocumentVersion is null!");			
		}
				
		String oldPath = dv.getPath();
		String path = ArchiveRouterConstants.ARCHIVE_LOCATION + "/" + dv.getPath();
		
		if(oldPath.contains("san"))
			return;
		
		File f = new File(dv.getPath());
		File newF = new File(path);
		
		if(!f.exists() && newF.exists())
		{
			//log.info(dv.getUuid() + " Didn't exist in old location but existed in ARCHIVE_LOCATION!");
			dv.setPath(path);
			dv = em.merge(dv);
			return;
		}
		
		try {
			if (move(dv.getPath(), path)) {
				dv.setPath(path);
				dv = em.merge(dv);
				reportDocumentCount.archiveSuccessfulEvent();			
			}else{					
				//throw new IOException("Moved failed for document version " + dv.getUuid());
				reportDocumentCount.archiveFailedEvent("Move failed");
			}
		}catch(IOException exn)
		{
			reportDocumentCount.archiveFailedEvent("IO Error " + exn.getLocalizedMessage());
		}

	}

	/**
	 * Moves a file, by first copying the file and then
	 * deleting the original.
	 * 
	 * @param sourceFilename The source filename.
	 * @param destinationFilename The destination filename.
	 * @return false if the move fails.
	 * @throws IOException if there was an error copying the file.
	 */
	public static boolean move(String sourceFilename,String destinationFilename) throws IOException {

		if(copy(sourceFilename,destinationFilename))
		{					
			if(!new File(destinationFilename).exists())
			{
				return false; 
			}
			if(!new File(sourceFilename).delete())
			{
				throw new IOException("Can't delete file " + sourceFilename);					
			}
			return true;
		}else{
			throw new IOException("Can't copy file " + sourceFilename + " " + destinationFilename);
		}
	}
	
	
	/**
	 * 
	 * @param sourceFilename The source filename.
	 * @param destinationFilename The destination filename.
	 * @return true if the copy is correct.
	 * @throws IOException if there was an error copying the file.
	 */
	public static boolean copy(String sourceFilename,String destinationFilename) throws IOException
	{		
		FileChannel srcChannel = null;
		FileChannel dstChannel = null;
		try {
			
			File f = new File(destinationFilename);
			if(!f.getParentFile().exists())
			{
				if(!f.getParentFile().mkdirs())
				{									
					throw new IOException("can't make parent directory " + f.getParent());					
				}
			}
			
		    // Create channel on the source
		    srcChannel = new FileInputStream(sourceFilename).getChannel();

		    // Create channel on the destination
		    dstChannel = new FileOutputStream(destinationFilename).getChannel();

		    // Copy file contents from source to destination
		    dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
	
		}finally{
		    // Close the channels
		    if(null != srcChannel)
				try {
					srcChannel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    if(null != dstChannel)
				try {
					dstChannel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return true;
	}
		
}
