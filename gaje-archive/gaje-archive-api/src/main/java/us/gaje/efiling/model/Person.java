package us.gaje.efiling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "person")
public class Person extends JpaIdEntity implements Persistable<Person>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Person> getPersistableClass() {
		return Person.class;
	}
	
	private String firstName;
	private String lastName;
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
