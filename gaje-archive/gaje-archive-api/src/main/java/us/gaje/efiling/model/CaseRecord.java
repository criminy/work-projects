package us.gaje.efiling.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import org.hibernate.annotations.SQLInsert;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import us.gaje.efiling.model.documents.Document;
import us.gaje.efiling.model.enums.CaseStatus;

@Entity
@Table(name="caseRecord")
@JsonAutoDetect
public class CaseRecord extends JpaIdEntity implements Persistable<CaseRecord>, Serializable {

	
	private static final long serialVersionUID = 1L;


	
	
	@ManyToOne
	@JoinColumn(name="courtUuid") private Court court;
	
	@Column(name="statusCode") private int _status;
	private String caseTrackingId;
	
	@Column(name="instantiationDateTime")
	private Date created;
	
	public Date getCreated() {
		return created;
	}
	
	public void setCreated(Date created) {
		this.created = created;
	}
	
	@Override
	public Class<CaseRecord> getPersistableClass() {
		return CaseRecord.class;
	}
	
	@Transient
	public CaseStatus getStatus() {
		return CaseStatus.fromValue(this._status);
	}

	public void setStatus(CaseStatus status) {
		this._status = status.getValue();
	}
	
	boolean clearFilingFlag = false;
	
	public boolean isClearFilingFlag() {
		return clearFilingFlag;
	}
	
	public void setClearFilingFlag(boolean clearFilingFlag) {
		this.clearFilingFlag = clearFilingFlag;
	}
	
	boolean deletedFilingFlag = false;
	
	public boolean isDeletedFilingFlag() {
		return deletedFilingFlag;
	}
	
	public void setDeletedFilingFlag(boolean deletedFilingFlag) {
		this.deletedFilingFlag = deletedFilingFlag;
	}

	
	
	@OneToMany
	@JoinColumn(name="caseRecordUuid")
	private List<OcssNumber> ocssNumbers = new ArrayList<OcssNumber>();
	
	@OneToMany
    @Sort(type = SortType.NATURAL)
    @OrderBy(value = "title DESC") //TODO: sort by creation date
	@JoinColumn(name="caseRecordUuid")
	private SortedSet<Document> documents = new TreeSet<Document>();

	@OneToMany
	@Sort(type = SortType.NATURAL)
	@OrderBy(value = "eventDateTime DESC") //TODO: sort by creation date
	@JoinColumn(name="caseRecordUuid")
	private SortedSet<Event> events = new TreeSet<Event>();
	
	@ManyToMany
//	@SQLInsert(sql="INSERT INTO personParticipants ( " +    			
//   			"	relationshipCode, " +
//   			"	caseRecordUuid, " +
//   			"	personUuid " +
//   		"	) VALUES ( " +   			
//   			"	4, " +
//   			"	?, " +
//   			"   ?  " +
//   			")"
//   	)
	@JoinTable(name="assoc_prosecutionAttorney_caseRecord",
			joinColumns=
				@JoinColumn(name="caseRecordUuid"),
			inverseJoinColumns=
				@JoinColumn(name="personUuid")				
	)
	private List<Attorney> plaintiffAttornies = new ArrayList<Attorney>();
	
	@ManyToMany
//	@SQLInsert(sql="INSERT INTO personParticipants ( " +    		
//   			"	relationshipCode, " +
//   			"	caseRecordUuid, " +
//   			"	personUuid " +
//   		"	) VALUES ( " +   		
//   			"	6, " +
//   			"	?, " +
//   			"   ?  " +
//   			")"
//   	)
	@JoinTable(name="assoc_filer_caseRecord",
			joinColumns=
				@JoinColumn(name="caseRecordUuid"),
			inverseJoinColumns=
				@JoinColumn(name="personUuid")				
	)
	private List<Person> filers = new ArrayList<Person>();
	
	
	@ManyToMany
	@JoinTable(name="assoc_defendantAttorney_caseRecord",
			joinColumns=
				@JoinColumn(name="caseRecordUuid"),
			inverseJoinColumns=
				@JoinColumn(name="personUuid")				
	)
//	@SQLInsert(sql="INSERT INTO personParticipants ( " + 
//   			"	relationshipCode, " +
//   			"	caseRecordUuid, " +
//   			"	personUuid " +
//   		"	) VALUES ( " +   			
//   			"	5, " +
//   			"	?, " +
//   			"   ?  " +
//   			")"
//   	)
	private List<Attorney> defendantAttornies = new ArrayList<Attorney>();
	
	@ManyToMany
//	@SQLInsert(sql="INSERT INTO personParticipants ( " + 
//   			"	relationshipCode, " +
//   			"	caseRecordUuid, " +
//   			"	personUuid " +
//   		"	) VALUES ( " +			
//   			"	3, " +
//   			"	?, " +
//   			"   ?  " +
//   			")"
//   	)
	@JoinTable(name="witnessParticipants",
			joinColumns=
				@JoinColumn(name="caseRecordUuid"),
			inverseJoinColumns=
				@JoinColumn(name="personUuid")				
	)
	private List<Person> witnesses = new ArrayList<Person>();
	
	@ManyToMany
//	@SQLInsert(sql="INSERT INTO personParticipants ( " +    			
//   			"	relationshipCode, " +
//   			"	caseRecordUuid, " +
//   			"	personUuid " +
//   		"	) VALUES ( " +   			
//   			"	1, " +
//   			"	?, " +
//   			"   ?  " +
//   			")"
//   	)
	@JoinTable(name="initiatingParticipants",
			joinColumns=
				@JoinColumn(name="caseRecordUuid"),
			inverseJoinColumns=
				@JoinColumn(name="personUuid")				
	)
	private List<Person> plaintiffs = new ArrayList<Person>();
	
	@ManyToMany
//	
//	@SQLInsert(sql="INSERT INTO personParticipants ( " +    			
//   			"	relationshipCode, " +
//   			"	caseRecordUuid, " +
//   			"	personUuid " +
//   		"	) VALUES ( " +		
//   			"	2, " +
//   			"	?, " +
//   			"   ?  " +
//   			")"
//   	)
//   	
	@JoinTable(name="defendantParticipants",
			joinColumns=
				@JoinColumn(name="caseRecordUuid"),
			inverseJoinColumns=
				@JoinColumn(name="personUuid")				
	)		
	private List<Person> defendants = new ArrayList<Person>();
	
	public List<Person> getDefendants() {
		return defendants;
	}
	
	public List<Person> getPlaintiffs() {
		return plaintiffs;
	}
	
	public List<Person> getWitnesses() {
		return witnesses;
	}
	
	public List<Person> getFilers() {
		return filers;
	}
	
	public void setFilers(List<Person> filers) {
		this.filers = filers;
	}
	
	public void setDefendantAttornies(List<Attorney> defendantAttornies) {
		this.defendantAttornies = defendantAttornies;
	}
	
	public void setDefendants(List<Person> defendants) {
		this.defendants = defendants;
	}
	
	public void setPlaintiffAttornies(List<Attorney> plaintiffAttornies) {
		this.plaintiffAttornies = plaintiffAttornies;
	}
	
	public void setPlaintiffs(List<Person> plaintiffs) {
		this.plaintiffs = plaintiffs;
	}

	public void setWitnesses(List<Person> witnesses) {
		this.witnesses = witnesses;
	}
	
	public SortedSet<Document> getDocuments() {
		return documents;
	}
	public void setDocuments(SortedSet<Document> documents) {
		this.documents = documents;
	}

	public String getCaseTrackingId() {
		return caseTrackingId;
	}	
	public void setCaseTrackingId(String caseTrackingId) {
		this.caseTrackingId = caseTrackingId;
	}
	
	public List<OcssNumber> getOcssNumbers() {
		return ocssNumbers;
	}
	public void setOcssNumbers(List<OcssNumber> ocssNumbers) {
		this.ocssNumbers = ocssNumbers;
	}
	
	public String getOcssNumber(){
		if(this.getOcssNumbers().size() == 0)
			return null;
		return this.getOcssNumbers().get(0).getExternalCaseRecordNumber();
	}
	
	public Court getCourt() {
		return court;
	}
	public void setCourt(Court court) {
		this.court = court;
	}
	


	

	public SortedSet<Event> getEvents() {
		return events;
	}
	public void setEvents(SortedSet<Event> events) {
		this.events = events;
	}

	public int get_status() {
		return _status;
	}
	
	public void set_status(int _status) {
		this._status = _status;
	}
	
	public List<Attorney> getDefendantAttornies() {
		return defendantAttornies;
	}
	
	public List<Attorney> getPlaintiffAttornies() {
		return plaintiffAttornies;
	}
	
	
}
