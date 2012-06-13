package us.gaje.efiling.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="personOrganizations")
@NamedQuery(name="byPersonAndCourt",
		query="from PersonOrganizations where person = :person and court = :court"
)
public class PersonOrganizations extends JpaIdEntity{

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="personUuid")
	private Person person;
	
	@ManyToOne
	@JoinColumn(name = "organizationUuid")
	private Organizations organization;
	
	@ManyToOne
	@JoinColumn(name = "courtUuid")
	private Court court;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Organizations getOrganization() {
		return organization;
	}

	public void setOrganization(Organizations organization) {
		this.organization = organization;
	}

	public Court getCourt() {
		return court;
	}

	public void setCourt(Court court) {
		this.court = court;
	}
	
	
	
}
