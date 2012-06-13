package us.gaje.efiling.api.business;

import us.gaje.efiling.api.UserEvent;
import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.enums.CaseStatus;

public class CaseIsAtAttorney {

	DaoManager daoManager;
	
	public CaseIsAtAttorney(DaoManager dao) {
		this.daoManager = dao;
	}
	
	
	public boolean isAtAttorney(UserEvent ev)
	{
		return isAtAttorney(ev.getCaseRecord(daoManager));
	}
	
	public boolean isAtAttorney(CaseRecord cr) {		
		CaseStatus cs = cr.getStatus();
		return cs.equals(CaseStatus.ReceivedByAttorney) || 
		   	   cs.equals(CaseStatus.Rejected) ||
		       cs.equals(CaseStatus.SentToAttorneyAddition) ||
		       cs.equals(CaseStatus.ReadyToSendToCourt) ||
		       cs.equals(CaseStatus.ATTORNEY);
	}
	
	@Override
	public String toString() {
		return "Case is at Attorney";
	}
	
}
