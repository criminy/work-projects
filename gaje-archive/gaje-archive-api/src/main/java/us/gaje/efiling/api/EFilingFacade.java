package us.gaje.efiling.api;

import us.gaje.efiling.model.CaseRecord;

public interface EFilingFacade {

	public void send(CaseRecord cr, String comment);
	public void reject(CaseRecord cr, String comment);

}