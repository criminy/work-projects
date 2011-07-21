package us.gaje.service;

import org.apache.log4j.Logger;

import us.gaaoc.framework.model.CaseRecord;
import us.gaaoc.framework.model.Documents;
import us.gaaoc.framework.model.constants.GajeConstants;
import us.gaje.service.pdf.DocumentValidator;

/**
 * A POJO Service for validating the case filing before transmission
 * @author artripa
 *
 */
public class CaseFilingValidator {

	Logger log = Logger.getLogger(CaseFilingValidator.class);
	
	public boolean validateCaseFiling(CaseRecord cf)
	{
		DocumentValidator documentValidator = new DocumentValidator();
		
		for(Documents d : cf.getDocuments())
		{
			if(d.getStatus().getCode() == GajeConstants.DocumentStatus.QUEUED_FOR_IMPORT &&
					!documentValidator.checkDocument(d))
			{
				log.warn("Case " + cf.getUuid() + " has an invalid document " + d.getUuid());
				return false;
			}
		}
		
		return true;
	}
	
	
}
