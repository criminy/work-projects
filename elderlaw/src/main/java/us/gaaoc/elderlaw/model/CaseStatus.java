package us.gaaoc.elderlaw.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class CaseStatus implements Serializable
{
	private Date startDate;
	private Date endDate;
	private String firstName;
	private String lastName;
	private String agency;
	private boolean status;
	private boolean story;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private County county;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	private LegalArea area;
	
	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	public String getId()
	{
		return id;
	}
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getStartDate()
	{
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(startDate);
	}
	
	@Transient
	public Date getStartDateDate()
	{
		return startDate;
	}
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}
	public String getEndDate()
	{
		return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(endDate);
	}
	
	@Transient
	public Date getEndDateDate()
	{
		return endDate;
	}
	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}
	public String getFirstName()
	{
		return firstName;
	}
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}
	public String getLastName()
	{
		return lastName;
	}
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}
	public String getAgency()
	{
		return agency;
	}
	public void setAgency(String agency)
	{
		this.agency = agency;
	}
	public boolean isStatus()
	{
		return status;
	}
	public void setStatus(boolean status)
	{
		this.status = status;
	}
	public boolean isStory()
	{
		return story;
	}
	public void setStory(boolean story)
	{
		this.story = story;
	}
	public County getCounty()
	{
		return county;
	}
	public void setCounty(County county)
	{
		this.county = county;
	}
	public LegalArea getArea()
	{
		return area;
	}
	public void setArea(LegalArea area)
	{
		this.area = area;
	}
}
