package us.gaje.service.validation;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import us.gaaoc.framework.model.Documents;

import com.lowagie.text.pdf.PdfReader;

/**
 * A POJO service for validating documents by
 * making sure they are loadable-PDFs.
 * @author artripa
 *
 */
@Component
public class DocumentValidator {

	Logger log = Logger.getLogger(DocumentValidator.class);
	
	public boolean checkDocument(Documents doc)
	{
		PdfReader reader = null;
		
		try{
			reader = new PdfReader(doc.getContent());
			return true;
		}catch(Throwable thr) {
			log.warn(thr.getMessage());
			thr.printStackTrace();
			return false;
		}finally{
			if(reader != null) reader.close();
		}
	}
	
}
