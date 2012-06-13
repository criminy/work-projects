package us.gaje.efiling.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="attorney")
public class Attorney extends JpaIdEntity implements Persistable<Attorney>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Attorney> getPersistableClass() {
		return Attorney.class;
	}

    
    private String barId;
    
    @ManyToOne
    @JoinColumn(name="personUuid")
    private Person person;
    
    public String getBarId() {
		return barId;
	}
    
    public void setBarId(String barId) {
		this.barId = barId;
	}
    
    public Person getPerson() {
		return person;
	}
    
    public void setPerson(Person person) {
		this.person = person;
	}

	
}
