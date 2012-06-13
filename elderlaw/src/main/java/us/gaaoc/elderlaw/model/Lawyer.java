package us.gaaoc.elderlaw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity
public class Lawyer implements Serializable
{
	private String name;
	private String email;
	private String phone;
	private String address;
	private String standing;
	private String barNumber;
	
	@Id
	private String id;
	
	private boolean presentation;
	private boolean insurance;
	
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="joinedLawyerComment", joinColumns=@JoinColumn(name="lawyer_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="comment_id", referencedColumnName="id"))
	private List<Comment> comments = new ArrayList<Comment>();
	
	
	//ManyToMany and LazyCollection set this way because of hibernate bug http://www.jroller.com/eyallupu/entry/solving_the_simultaneously_fetch_multiple1
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="joinedLawyerCounty", joinColumns=@JoinColumn(name="lawyer_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="county_id", referencedColumnName="id"))
	private List<County> counties = new ArrayList<County>();
	
	@ManyToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE) 
	@JoinTable(name="joinedLawyerArea", joinColumns=@JoinColumn(name="lawyer_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="area_id", referencedColumnName="id"))
	private List<LegalArea> areas = new ArrayList<LegalArea>();
	
	@OneToMany(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JoinTable(name="joinedLawyerCaseStatus", joinColumns=@JoinColumn(name="lawyer_id", referencedColumnName="id"), inverseJoinColumns=@JoinColumn(name="casestatus_id", referencedColumnName="id"))
	private List<CaseStatus> statuses = new ArrayList<CaseStatus>();
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getAddress()
	{
		return address;
	}
	public void setAddress(String address)
	{
		this.address = address;
	}
	public String getStanding()
	{
		return standing;
	}
	public void setStanding(String standing)
	{
		this.standing = standing;
	}
	public boolean isInsurance()
	{
		return insurance;
	}
	public void setInsurance(boolean insurance)
	{
		this.insurance = insurance;
	}
	public String getBarNumber()
	{
		return barNumber;
	}
	public void setBarNumber(String barNumber)
	{
		this.barNumber = barNumber;
	}
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	public boolean isPresentation()
	{
		return presentation;
	}
	public void setPresentation(boolean presentation)
	{
		this.presentation = presentation;
	}
	public List<Comment> getComments()
	{
		return comments;
	}
	public void setComments(List<Comment> comments)
	{
		this.comments = comments;
	}
	public List<County> getCounties()
	{
		return counties;
	}
	public void setCounties(List<County> counties)
	{
		this.counties = counties;
	}
	public List<LegalArea> getAreas()
	{
		return areas;
	}
	public void setAreas(List<LegalArea> areas)
	{
		this.areas = areas;
	}
	public List<CaseStatus> getStatuses()
	{
		return statuses;
	}
	public void setStatuses(List<CaseStatus> statuses)
	{
		this.statuses = statuses;
	}
}
