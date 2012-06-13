package us.gaje.efiling.api;

import us.gaje.efiling.dao.DaoManager;

public class BaseDaoProcessor
{
	
	private DaoManager daoManager;
	
	public DaoManager getDaoManager() {
		return daoManager;
	}
	public BaseDaoProcessor(DaoManager daoManager) {
		this.daoManager = daoManager;
	}
}