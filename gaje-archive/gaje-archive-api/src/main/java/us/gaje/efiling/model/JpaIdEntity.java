package us.gaje.efiling.model;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;

import us.gaje.efiling.api.IdEntity;

@MappedSuperclass
public class JpaIdEntity implements IdEntity, Serializable{

	private static final long serialVersionUID = 1L;
	@Id
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @GeneratedValue(generator = "system-uuid")
    private String uuid;
	
    @Override
    public String getId() {
    	return this.getUuid();
    }
    
    public String getUuid() {
		return uuid;
	}
    public void setUuid(String uuid) {
		this.uuid = uuid;
	}
    
}
