package us.gaje.service.impl.jpa;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import us.gaaoc.gajews.types.*;

import us.gaaoc.framework.model.CaseRecord;
import us.gaaoc.framework.model.Court;
import us.gaaoc.framework.model.CourtEvents;
import us.gaaoc.framework.model.Documents;
import us.gaaoc.framework.model.LocalCourtEventType;
import us.gaaoc.framework.model.LocalParticipantRelationship;
import us.gaaoc.framework.model.OrgParticipants;
import us.gaaoc.framework.model.OrganizationAddress;
import us.gaaoc.framework.model.Organizations;
import us.gaaoc.framework.model.Person;
import us.gaaoc.framework.model.PersonAddress;
import us.gaaoc.framework.model.constants.GajeConstants.DocumentStatus;
import us.gaje.service.validation.DocumentValidator;


import javax.persistence.*;

import org.apache.log4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * This is the DomToSustainConverter which
 * reads the DOM from the JPA EntityManager.
 * 
 * 
 * @author artripa
 *
 */
@Component
@Transactional
public class DomToWebserviceConverterJPA {
	
	Logger log = Logger.getLogger(DomToWebserviceConverterJPA.class);
	
	@Autowired(required=true) private DocumentValidator documentValidator;
	@PersistenceContext javax.persistence.EntityManager entityManager;
			
	
	@Transactional
	public CaseFiling createCaseFiling(CaseRecord cr, Court court) {

		// 1. load data
		// 		b7006e6c-695c-11dd-945f-00301b45ff56	 
		//Hibernate.initialize(cr.getPersonParticipants());
		//Hibernate.initialize(cr.getOrgParticipants());
		//Hibernate.initialize(cr.getCourtEvents());
		
		List<OrgParticipants> oParticipantsList = cr.getOrgParticipants();
		//List<CourtEvents> courtEventList = cr.getCourtEvents();

		List<OrgParticipants> initiatingPartyOrganizationList = new ArrayList<OrgParticipants>();
		
		Set<Person> initiatingPartyPersonList = cr.getPlaintiffs();
		Set<Person> defendentPartyPersonList = cr.getDefendants();
		Set<Person> witnessPersonList = cr.getWitnesses();
		Set<Person> plantiffsAttorneyList = cr.getPlaintiffAttorney();
		Set<Person> defendentsAttorneyList = cr.getDefendantAttorney();

		for (OrgParticipants o : oParticipantsList) {
			if (o.getRelationship().getCode() == 1) // for initiating party
				// organization list
				initiatingPartyOrganizationList.add(o);
		}

		// 2. transfer data into CMS objects

		CaseFiling caseFiling = new CaseFiling();
		caseFiling.setCategory(cr.getLocalCaseCategory().getCmsCode());
		caseFiling.setDivID(cr.getLocalCaseDivision().getCmsCode());
		caseFiling.setType(cr.getLocalCaseType().getCmsCode());
		caseFiling.setFilingType(cr.getLocalCaseFilingType().getCmsCode());
		// caseFiling.setDate(new Date());
		DateFormat df = DateFormat.getDateInstance();
		Date sd = getAttorneySubmissionDate(cr.getUuid());
		if(sd == null)
		{
			caseFiling.setDate("");
		}else{
			caseFiling.setDate(df.format(getAttorneySubmissionDate(cr.getUuid())));
		}
		

		caseFiling.setCourtID(court.getId());
		
		// a. for initiating party organization list

		CasePartyList ipoList = new CasePartyList();
		
		LocalParticipantRelationship localParticipantRelationship = (LocalParticipantRelationship) entityManager.createQuery(
		"from LocalParticipantRelationship r where r.court = :court and r.participantRelationship.code = :code")
		.setParameter("court",court)
		.setParameter("code",1)
		.getSingleResult();
		
		for (OrgParticipants o : initiatingPartyOrganizationList) {

			String orgName = o.getOrganizations().getName().replaceAll("[^a-zA-Z0-9 ]","");

			AddressList addList = new AddressList();
			List<OrganizationAddress> orgAddList = o.getOrganizations()
			.getAddresses();
			for (OrganizationAddress orgAdd : orgAddList) {
				AddressType add = new AddressType();
				add.setStreet1(orgAdd.getAddress().getStreetFullText());
				add.setStreet2(orgAdd.getAddress().getStreetExtensionText());
				add.setCity(orgAdd.getAddress().getCityName());
				add.setState(orgAdd.getAddress().getState());
				add.setZip(orgAdd.getAddress().getZipCode());
				
				addList.getAddress().add(add);
			}

			OrganizationType org = new OrganizationType();
			org.setAddresses(addList);
			org.setName(orgName);
			CasePartyType cp = new CasePartyType();
			cp.setOrganization(org);

			cp.setCmsTypeCode(localParticipantRelationship.getCmsCode()); // @todo
			
			ipoList.getCaseParty().add(cp);
		}
		caseFiling.setCaseInitiatingPartyOrganizations(ipoList);		
				
		// set case caption
		caseFiling.setCaption(cr.getCaseCaption());
		
		// b. for initiating party person list
		CasePartyList ippList = getCMSParties(initiatingPartyPersonList,
				court, 1);
		caseFiling.setInitiatingParties(ippList);

		// c. for defendent party person list
		CasePartyList dppList = getCMSParties(defendentPartyPersonList,
				court, 2);
		caseFiling.setCaseDefendantParties(dppList);

		// d. for witness list
		CasePartyList witList = getCMSParties(witnessPersonList, court,
				3);
		caseFiling.setWitnesses(witList);

		// e. for plantiff attorney
		CaseOfficialList paList = getCMSOfficial(plantiffsAttorneyList,
				court, 4);
		caseFiling.setProsecutionAttorneys(paList);

		// f. for defendent attorney List
		CaseOfficialList daList = getCMSOfficial(defendentsAttorneyList,
				court, 5);
		caseFiling.setDefenseAttorneys(daList);

		// g. for scheduled court date
		CaseEventList eventList = new CaseEventList();
		CourtEvents evt = cr.getCourtDate();
		if(evt != null)
		{
			LocalCourtEventType lcet = (LocalCourtEventType) entityManager.createQuery("from LocalCourtEventType e where e.court = :court and e.courtEventType.code = :code")
				.setParameter("court",court)
				.setParameter("code",1)
				.getSingleResult();
			//CourtEventType cet = evt.getEventType();
			CaseEventType caseEvent = new CaseEventType();
			df = DateFormat.getDateInstance();
			caseEvent.setDate(df.format(evt.getEventTime()));
			caseEvent.setType(lcet.getCmsCode()); 
			eventList.getCaseEvent().add(caseEvent);
		}		
		caseFiling.setEvents(eventList);		
		
		caseFiling.setDocketID(cr.getCaseTrackingId());
			
		
		
		{//j. override caseFilingType if the documents are of a specific type.
		 //useful for proper case counting and mapping from our "CaseRecord"
		 //to the concept of a 'CaseFiling' in case count.
			
			String caseFilingType = cr.getLocalCaseFilingType().getCmsCode();
					
			{// h. for documents
				DocumentList dl = new DocumentList();
				
				for(Documents d : cr.getDocuments())
				{			
					//only add queued documents.
					if(d.getStatus().getCode() == DocumentStatus.QUEUED_FOR_IMPORT)
					{
						if(documentValidator.checkDocument(d))
						{
							DocumentType dt = new DocumentType();
							dt.setContent(d.getContent());			
							dt.setTitle(d.getTitle());
							dt.setType(d.getDocumentInstance().getDocLocalCode().getCode());
							dt.setUuid(d.getUuid());
							
							dl.getDocument().add(dt);
												
							String newCaseFilingTypeCode = (String) entityManager.createNativeQuery("select caseFilingTypeCode from documentInstance where code = :code")
								.setParameter("code",d.getDocumentInstance().getCode())
								.getSingleResult();
							
							if(newCaseFilingTypeCode != null)
							{
								caseFilingType = newCaseFilingTypeCode;
							}
												
						}else{
							throw new RuntimeException("Document on case is not valid");
						}
					}
				}
				caseFiling.setDocuments(dl);
			}
							
	
			caseFiling.setFilingType(caseFilingType);
		}
		
		// i. for local case number
		caseFiling.setFilerOrganizationTrackingId(cr.getFilingNumber());
		
		return caseFiling;
	}
	
	public String getBarID(Person person, us.gaaoc.framework.model.Court court) {			
		Organizations o = (Organizations) entityManager.createQuery("select o.organization from PersonOrganizations o where o.court = :court and o.person = :person")
			.setParameter("court",court)
			.setParameter("person",person)
			.getSingleResult();

		String barID = (String) entityManager.createQuery("select a.barID from Attorney a where a.person = :person and a.organization = :org")
			.setParameter("person",person)
			.setParameter("org",o)
			.getSingleResult();
		
		return barID;
	}
	
	/**
	 * This method transforms a PersonParticipants list into a CaseOfficial list
	 * for import to sustain.
	 * 
	 * @param participantList
	 * @return
	 */
	private CaseOfficialList getCMSOfficial(			
			Collection<Person> participantList, Court court,
			int participantRelationshipCode) {

//		LocalParticipantRelationship localParticipantRelationship = mf.findLocalParticipantRelationship(court,
//				participantRelationshipCode);

		LocalParticipantRelationship localParticipantRelationship = (LocalParticipantRelationship) entityManager.createQuery(
			"from LocalParticipantRelationship r where r.court = :court and r.participantRelationship.code = :code")
			.setParameter("court",court)
			.setParameter("code",participantRelationshipCode)
			.getSingleResult();
	
		CaseOfficialList caseOfficialList = new CaseOfficialList();
		for (Person poff : participantList) {
			
			String firstName = poff.getFirstName().replaceAll("[^a-zA-Z0-9 ]","");								
			String middleName = "";
			if(	poff.getMiddleName() != null)
			{
				middleName = poff.getMiddleName().replaceAll("[^a-zA-Z0-9 ]","");
			}
			String lastName = poff.getLastName().replaceAll("[^a-zA-Z0-9 ]","");
			String suffix = "";
			if(	poff.getMiddleName() != null)
			{
				suffix = poff.getSuffixName().replaceAll("[^a-zA-Z0-9 ]","");
			}
			//String barId = off.getPerson().getAttorney().getBarID();
			String barId = getBarID(poff,court);
			PersonType p = new PersonType();
			/*
			
			p.setAddresses(new AddressList());
			for(PersonAddress a : off.getPerson().getAddresses())
			{
				AddressType t = new AddressType();
				t.setCity(a.getAddress().getCityName());
				t.setState(a.getAddress().getState());
				t.setStreet1(a.getAddress().getStreetFullText());
				t.setStreet2(a.getAddress().getStreetExtensionText());
				t.setZip(a.getAddress().getZipCode());
				p.getAddresses().getAddress().add(t);
			}	
			*/				
			
			p.setFirstName(firstName);
			p.setLastName(lastName);
			p.setMiddleName(middleName);
			p.setSuffix(suffix);
			
			CaseOfficial co = new CaseOfficial();
			co.setBarID(barId);
			
			co.setCmsCode(localParticipantRelationship.getCmsCode());
			co.setPerson(p);
			caseOfficialList.getCaseOfficial().add(co);
		}
		return caseOfficialList;
	}

	/**
	 * This method transforms a Person participant list into a case party list
	 * for import to sustain.
	 * 
	 * @param participantList
	 * @return
	 */
	private CasePartyList getCMSParties(
			
			Collection<Person> participantList, Court court,
			int participantRelationshipCode) {

		LocalParticipantRelationship localParticipantRelationship = (LocalParticipantRelationship) entityManager.createQuery(
			"from LocalParticipantRelationship r where r.court = :court and r.participantRelationship.code = :code")
			.setParameter("court",court)
			.setParameter("code",participantRelationshipCode)
			.getSingleResult();

		CasePartyList casePartyList = new CasePartyList();
		for (Person pp : participantList) {
			String firstName = pp.getFirstName().replaceAll("[^a-zA-Z]","");
			String middleName;
			if(pp.getMiddleName() == null)
			{
				middleName = "";
			}else{
				middleName = pp.getMiddleName().replaceAll("[^a-zA-Z]","");
			}
			
			
			
			
			
			
			String lastName = pp.getLastName().replaceAll("[^a-zA-Z]","");
			String suffix;
			if(pp.getSuffixName() == null)
				{
				suffix = "";
				
				}else{
					suffix = pp.getSuffixName().replaceAll("[^a-zA-Z]","");	
				}

			AddressList addList = new AddressList();
			pp.getAddresses();
			List<PersonAddress> pAddList = pp.getAddresses();
			for (PersonAddress pa : pAddList) {
				AddressType add = new AddressType();
				
				 add.setStreet1(pa.getAddress().getStreetFullText());
				 add.setStreet2(pa.getAddress().getStreetExtensionText());
				 add.setCity(pa.getAddress().getCityName()); 
				 add.setState(pa.getAddress().getState());
				 add.setZip(pa.getAddress().getZipCode());
				 
				addList.getAddress().add(add);
			}
			PersonType p = new PersonType();
			
			p.setFirstName(firstName);
			p.setLastName(lastName);
			p.setMiddleName(middleName);
			p.setSuffix(suffix);
			p.setAddresses(addList);
			
			CasePartyType cp = new CasePartyType();
			cp.setCmsTypeCode(localParticipantRelationship.getCmsCode()); // @todo
			cp.setPerson(p);
			casePartyList.getCaseParty().add(cp);
		}
		return casePartyList;
	}
	

	private Date getAttorneySubmissionDate(String caseUuid) {
		//Date attorneySubmissionDate = null;
		//attorneySubmissionDate = mf.findAttorneySubmissionDate(caseUuid);
		
		//TODO: replace with an HQL or JPAQL query.
		//TODO: replace with a hibernate mapping
		try {
			String sql = "SELECT  MAX(crh.eventDateTime) "
				+ "FROM caseRecordHistory crh, caseRecordEventType cret "
				+ "WHERE crh.caseRecordUuid= :caseRecord "
				+ "AND crh.eventTypeCode=1 "
				+ "AND crh.eventTypeCode=cret.code "
				+ "GROUP BY crh.caseRecordUuid";
			return (Date) entityManager.createNativeQuery(sql)
			
			.setParameter("caseRecord",caseUuid)
			.getSingleResult();
		}catch(NoResultException exn)
		{
			return null;
		}
	}

	public void setEntityManager(EntityManager entityManager2) {
		this.entityManager = entityManager2;		
	}
	
	public void setDocumentValidator(DocumentValidator documentValidator) {
		this.documentValidator = documentValidator;
	}
	
}
