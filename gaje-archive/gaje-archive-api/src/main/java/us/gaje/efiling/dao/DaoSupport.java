package us.gaje.efiling.dao;

public class DaoSupport {

	private DaoManager daoManager;
	
	public DaoManager getDaoManager() {
		return daoManager;
	}
	
	public DaoSupport(DaoManager daoManager) {
		this.daoManager = daoManager;
	}
	
	public <T> Dao<T> getDao(Class<T> clazz)
	{
		return daoManager.get(clazz);
	}
	
	
	
}
