package us.gaje.efiling.api;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import us.gaje.efiling.dao.DaoManager;
import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.Person;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown=true)
public class UserEvent implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean readOnly;
	private Date sendDate;
	private String personId;
	transient private Person sendingPerson;
	
	private String caseRecordId;
	transient private CaseRecord caseRecord;
	private String comment;
	
	public boolean isReadOnly() {
		return readOnly;
	}
	
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	public Date getSendDate() {
		return sendDate;
	}
	public void setSendDate(Date sendDate) {
		this.sendDate = sendDate;
	}

	public Person getSendingPerson(DaoManager manager)
	{
		return manager.get(Person.class).read(personId);
	}
	
	public void setSendingPerson(Person p)
	{
		this.personId = p.getUuid();
		this.sendingPerson = p;
	}
	
	public CaseRecord getCaseRecord(DaoManager manager)
	{
		return caseRecord = manager.get(CaseRecord.class).read(caseRecordId);		
	}
	
	public void setCaseRecord(CaseRecord cr)
	{
		this.caseRecordId = cr.getUuid();
		this.caseRecord = cr;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getCaseRecordId() {
		return caseRecordId;
	}
	
	public String getPersonId() {
		return personId;
	}
	public void setCaseRecordId(String caseRecordId) {
		this.caseRecordId = caseRecordId;
	}
	
	public void setPersonId(String personId) {
		this.personId = personId;
	}
	
	
}
