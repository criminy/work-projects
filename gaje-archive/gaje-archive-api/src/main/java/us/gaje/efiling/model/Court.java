package us.gaje.efiling.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "court")
public class Court extends JpaIdEntity implements Persistable<Court> {

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Court> getPersistableClass() {
		return Court.class;
	}
    
	@Column(name="id")
	private String externalId;
	
    private String name;
    
    @Transient
    private String level = "superior";
    
    @Transient
    public String getDescription()
    {    	
    	return String.format("%s County %s Court",
    		StringUtils.uppercaseWord(name.split(" ")[0]),
    		StringUtils.uppercaseWord(level));
    }
    
    public String getName() {
		return name;
	}
    public void setName(String name) {
		this.name = name;
	}
    
    
    public String getExternalId() {
		return externalId;
	}
    public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

 
}
