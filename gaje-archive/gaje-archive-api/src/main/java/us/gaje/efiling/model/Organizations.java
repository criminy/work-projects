package us.gaje.efiling.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="organizations")
public class Organizations extends JpaIdEntity{

	private static final long serialVersionUID = 1L;
	private String name;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
}
