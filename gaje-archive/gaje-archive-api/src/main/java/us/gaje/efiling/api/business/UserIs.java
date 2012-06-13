package us.gaje.efiling.api.business;

import us.gaje.efiling.api.UserEvent;
import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.dao.DaoSupport;
import us.gaje.efiling.model.Attorney;
import us.gaje.efiling.model.LegalPreparer;
import us.gaje.efiling.model.Person;
import us.gaje.efiling.model.enums.Roles;

public class UserIs extends DaoSupport {

	private Roles role;	
	
	public UserIs(DaoManager dao,Roles role) {
		super(dao);		
		this.role = role;
	}

	public boolean userIs(UserEvent ue)
	{
		return userIs(ue.getSendingPerson(this.getDaoManager()));
	}
	
	public boolean userIs(Person p)
	{
		Class<?> clazz;
		if(role.equals(Roles.AGENT))
		{
			clazz = LegalPreparer.class;

		}else if(role.equals(Roles.ATTORNEY))
		{
			clazz = Attorney.class;
		}else if(role.equals(Roles.CLERK))
		{
			clazz = null;
			throw new UnsupportedOperationException("Can't check clerk role yet");						
		}else if(role.equals(Roles.JUDGE))
		{
			clazz = null;
			throw new UnsupportedOperationException("Can't check judge role yet");						
		}else{
			throw new UnsupportedOperationException("No valid role");
		}
		
		return !(this.getDao(clazz).byAttribute("person",p).size() == 0);
	}
	
	@Override
	public String toString() {
		return "User = " + role.getDescription();
	}
	
}
