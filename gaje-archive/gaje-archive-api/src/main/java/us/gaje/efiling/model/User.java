package us.gaje.efiling.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ef_user")
public class User extends JpaIdEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	private String username;
	private String primaryRole;
	
	public String getPrimaryRole() {
		return primaryRole;
	}
	
	public void setPrimaryRole(String primaryRole) {
		this.primaryRole = primaryRole;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}
	
}
