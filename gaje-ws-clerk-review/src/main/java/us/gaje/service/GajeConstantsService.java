package us.gaje.service;

import us.gaaoc.framework.model.CaseRecordEventType;
import us.gaaoc.framework.model.CaseRecordStatus;
import us.gaaoc.framework.model.Court;
import us.gaaoc.framework.model.CourtEventType;
import us.gaaoc.framework.model.DocumentInstance;
import us.gaaoc.framework.model.DocumentStatus;

/**
 * Service interface for looking up constant values.
 * 
 * @author artripa
 *
 */
public interface GajeConstantsService {

	public CaseRecordEventType getCaseRecordEventType(int id);
	
	public DocumentStatus getDocumentStatus(int id);
	
	public CourtEventType getCourtEventType(int id);
	
	public CaseRecordStatus getCaseRecordStatus(int id);
	
	public DocumentInstance getDocumentInstanceByLocalCode(String localCode,Court court);
}
