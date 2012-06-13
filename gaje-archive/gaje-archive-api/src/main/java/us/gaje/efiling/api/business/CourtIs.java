package us.gaje.efiling.api.business;

import us.gaje.efiling.api.UserEvent;
import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.Court;
import us.gaje.efiling.model.enums.Counties;

public class CourtIs {

	private Counties county;
	private DaoManager dao;
	
	public CourtIs(DaoManager dao,Counties county) {
			this.county = county;
			this.dao = dao;
	}
	
	public boolean courtIs(UserEvent ue)
	{
		return courtIs(ue.getCaseRecord(dao));
	}
	
	public boolean courtIs(CaseRecord cr)
	{
		return courtIs(cr.getCourt());
	}
	
	public boolean courtIs(Court court)
	{
		return court.getUuid().equals(county.getDescription());
	}
	
	
	@Override
	public String toString() {
		return "Court = " + county.getDescription();
	}
	
}
