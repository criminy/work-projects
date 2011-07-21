package us.gaje.model.stamping;

/*
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
*/

import us.gaaoc.framework.model.Court;

//@Entity
//@Table(name="gaje_ws_court_stamp_metadata")
public class CourtStampMetaData {

//	@Id @GeneratedValue
	private int id;
	
//	@OneToOne(optional=false)
//	@JoinColumn(name="court_uuid",nullable=false,unique=false)
	private Court court;
	
//	@Column(name="text",nullable=false,unique=false)
	private String courtStampText;
	
//	@Column(name="y_position",nullable=false,unique=false)
	private float y_position;
	
//	@Column(name="x_position",nullable=false,unique=false)
	private float x_position;
	
	public Court getCourt() {
		return court;
	}
	public void setCourt(Court court) {
		this.court = court;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setCourtStampText(String courtStampText) {
		this.courtStampText = courtStampText;
	}

	public void setY_position(float y_position) {
		this.y_position = y_position;
	}

	public void setX_position(float x_position) {
		this.x_position = x_position;
	}

	public String getCourtStampText() {
		return courtStampText;
	}

	public float getY_position() {
		return y_position;
	}

	public float getX_position() {
		return x_position;
	}
	
}
