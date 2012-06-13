package us.gaje.efiling.api.business;

import us.gaje.efiling.api.UserEvent;
import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.enums.CaseStatus;

public class CaseIsAtClerk {

	DaoManager daoManager;
	
	public CaseIsAtClerk(DaoManager dao) {
		this.daoManager = dao;
	}
	
	public boolean isAtClerk(UserEvent ev)
	{
		return isAtClerk(ev.getCaseRecord(daoManager));
	}
	
	public boolean isAtClerk(CaseRecord cr) {		
		CaseStatus cs = cr.getStatus();
		return cs.equals(CaseStatus.FailedToImport) ||
		   	   cs.equals(CaseStatus.Received) ||
		       cs.equals(CaseStatus.ReceivedByClerk) ||
		       cs.equals(CaseStatus.ReadyToImport) ||
		       cs.equals(CaseStatus.CLERK);
	}
	
	@Override
	public String toString() {
		return "Case is at clerk";
	}
	
}
