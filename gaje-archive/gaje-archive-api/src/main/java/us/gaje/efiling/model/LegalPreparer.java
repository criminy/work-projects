package us.gaje.efiling.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "legalPreparer")
@NamedQuery(name="byPersonAndOrganization",
		query="from LegalPreparer where person = :person and organization = :organization")
public class LegalPreparer extends JpaIdEntity{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name = "personUuid")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "attorneyUuid")
	private Attorney attorney;
	
	@ManyToOne
	@JoinColumn(name = "orgUuid")
	private Organizations organization;
	
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	public void setAttorney(Attorney attorney) {
		this.attorney = attorney;
	}
	
	public Attorney getAttorney() {
		return attorney;
	}
	
	public Organizations getOrganization() {
		return organization;
	}
	
	public void setOrganization(Organizations organization) {
		this.organization = organization;
	}
}
