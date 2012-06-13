package us.gaje.efiling.model.documents;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import us.gaje.efiling.model.Court;
import us.gaje.efiling.model.JpaIdEntity;

/**
 * A court-specific instance of a re-usable document type. 
 * @author artripa
 *
 */
@Entity
@Table(name="documentInstance")
public class DocumentInstance implements Serializable
{
	
	@Id
	private Long code;

	public Long getCode() {
		return code;
	}
	
	public void setCode(Long code) {
		this.code = code;
	}
	
	private static final long serialVersionUID = 1L;

	private String description;
	
	@ManyToOne
	@JoinColumn(name="courtUuid")
	private Court court;
	
	
	public Court getCourt() {
		return court;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setCourt(Court court) {
		this.court = court;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
