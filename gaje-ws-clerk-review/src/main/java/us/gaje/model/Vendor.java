package us.gaje.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import us.gaaoc.framework.model.Court;

/**
 * Vendor entity which represents a user
 * who can interface with the webservice.
 * @author artripa
 *
 */
@Entity
@Table(name="gaje_ws_auth_vendor_court_access")
public class Vendor {
	@Id @GeneratedValue
	private int id;
	
	@Column(name="username",nullable=false,unique=false)
	private String username;
	
	@ManyToOne(optional=false)
	@JoinColumn(name="court_uuid",nullable=false,unique=false)
	private Court court;
	
	public void setCourt(Court court) {
		this.court = court;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Court getCourt() {
		return court;
	}
	public int getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
}
