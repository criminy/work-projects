package us.gaje.efiling.model;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import javax.persistence.Entity;

@Entity
@Table(name="caseRecordIdExternalCaseRecordNumber")
public class OcssNumber extends JpaIdEntity {
	
	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="caseRecordUuid")
	private CaseRecord caseRecord;
	
	private String externalCaseRecordNumber;
	
	private String organizationUuid;
	
	public void setOrganizationUuid(String organizationUuid) {
		this.organizationUuid = organizationUuid;
	}
	
	public String getOrganizationUuid() {
		return organizationUuid;
	}
	
	public CaseRecord getCaseRecord() {
		return caseRecord;
	}
	public String getExternalCaseRecordNumber() {
		return externalCaseRecordNumber;
	}

	public void setCaseRecord(CaseRecord caseRecord) {
		this.caseRecord = caseRecord;
	}
	public void setExternalCaseRecordNumber(String externalCaseRecordNumber) {
		this.externalCaseRecordNumber = externalCaseRecordNumber;
	}

}
