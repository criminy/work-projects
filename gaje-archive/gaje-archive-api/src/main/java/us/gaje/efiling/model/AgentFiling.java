package us.gaje.efiling.model;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import us.gaje.efiling.model.enums.CaseStatus;


/**
 * This is a read-only data model class used for the view-layer.
 * @author artripa
 */
@Entity
@Table(name = "AgentFilingTable")
public class AgentFiling 
{
	@Id
	private String caseRecordUuid;
	private String caseTrackingId;
	private Date created;
	@Column(name = "statusCode")
	private int statusCode;
	@Column(name = "lastEventDateTime")
	private Date lastEventTime;
	@Column(name = "lastEventDescription")
	private String lastEventStatus;
	private String courtUuid;
	private String caseRecordStatusDescription;
	private String filerUuid;
	private String filerName;
	private String defendantFirstName;
	private String defendantLastName;
	
	public String getCaseRecordUuid() {
		return caseRecordUuid;
	}
	public void setCaseRecordUuid(String caseRecordUuid) {
		this.caseRecordUuid = caseRecordUuid;
	}
	public String getCaseTrackingId() {
		return caseTrackingId;
	}
	public void setCaseTrackingId(String caseTrackingId) {
		this.caseTrackingId = caseTrackingId;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	
	public int getStatusCode() {
		return statusCode;
	}
	
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	@Transient
	public CaseStatus getStatus() {
		return CaseStatus.fromValue(this.statusCode);
	}
	public void setStatus(CaseStatus status) {
		this.statusCode = status.getValue();
	}
	public Date getLastEventTime() {
		return lastEventTime;
	}
	public void setLastEventTime(Date lastEventTime) {
		this.lastEventTime = lastEventTime;
	}
	public String getLastEventStatus() {
		return lastEventStatus;
	}
	public void setLastEventStatus(String lastEventStatus) {
		this.lastEventStatus = lastEventStatus;
	}
	public void setCourtUuid(String courtUuid) {
		this.courtUuid = courtUuid;
	}
	public String getCourtUuid() {
		return courtUuid;
	}
	public void setCaseRecordStatusDescription(
			String caseRecordStatusDescription) {
		this.caseRecordStatusDescription = caseRecordStatusDescription;
	}
	public String getCaseRecordStatusDescription() {
		return caseRecordStatusDescription;
	}
	public void setFilerName(String filerName) {
		this.filerName = filerName;
	}
	public String getFilerName() {
		return filerName;
	}
	public void setFilerUuid(String filerUuid) {
		this.filerUuid = filerUuid;
	}
	public String getFilerUuid() {
		return filerUuid;
	}
	public void setDefendantFirstName(String defendantFirstName) {
		this.defendantFirstName = defendantFirstName;
	}
	public String getDefendantFirstName() {
		return defendantFirstName;
	}
	public void setDefendantLastName(String defendantLastName) {
		this.defendantLastName = defendantLastName;
	}
	public String getDefendantLastName() {
		return defendantLastName;
	}
}
