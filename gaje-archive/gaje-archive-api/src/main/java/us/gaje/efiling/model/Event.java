package us.gaje.efiling.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


import us.gaje.efiling.model.enums.EventType;

@Entity
@Table(name = "caseRecordHistory")
public class Event extends JpaIdEntity implements Comparable<Event> {


	private static final long serialVersionUID = 1L;

	@Override
	public int compareTo(Event o) {
		return this.eventDateTime.compareTo(o.eventDateTime);
	}
	
	@ManyToOne
	@JoinColumn(name="personUuid")
	private Person person;
	
	private int eventTypeCode;
	
	private Date eventDateTime;
	
    @ManyToOne
    @JoinColumn(name = "caseRecordUuid")
    private CaseRecord caseRecord;
    
    public CaseRecord getCaseRecord() {
		return caseRecord;
	}
    public Date getEventDateTime() {
		return eventDateTime;
	}

    public void setCaseRecord(CaseRecord caseRecord) {
		this.caseRecord = caseRecord;
	}
    public void setEventDateTime(Date eventDateTime) {
		this.eventDateTime = eventDateTime;
	}

    
    @Transient
    public EventType getType()
    {
    	return EventType.fromValue(this.eventTypeCode);
    }
    
    public void setType(EventType ev)
    {
    	this.eventTypeCode = ev.getValue();
    }
    
    public Person getPerson() {
		return person;
	}
    public void setPerson(Person person) {
		this.person = person;
	}
    
    @Column(name="comments")
    private String comment;
    
    public String getComment() {
		return comment;
	}
    public void setComment(String comment) {
		this.comment = comment;
	}
    
}
