package us.gaje.efiling.api.business;

import us.gaje.efiling.api.UserEvent;
import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.enums.CaseStatus;

public class CaseIsAtAgent {

	DaoManager daoManager;
	
	public CaseIsAtAgent(DaoManager dao) {
		this.daoManager = dao;
	}
	
	public boolean isAtAgent(UserEvent ev)
	{
		return isAtAgent(ev.getCaseRecord(daoManager));
	}
	
	public boolean isAtAgent(CaseRecord cr) {		
		CaseStatus cs = cr.getStatus();
		return cs.equals(CaseStatus.Unsent) || 
		   	   cs.equals(CaseStatus.UnsentAddition) ||
		       cs.equals(CaseStatus.Imported) ||
		       cs.equals(CaseStatus.AGENT);
	}
	
	@Override
	public String toString() {
		return "Case is at Agent";
	}
	
}
