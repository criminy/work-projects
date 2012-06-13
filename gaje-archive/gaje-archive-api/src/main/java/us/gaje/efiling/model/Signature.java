package us.gaje.efiling.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import us.gaje.efiling.model.documents.Document;

@Entity
@Table(name="signatures")
public class Signature extends JpaIdEntity implements Comparable<Signature>,Persistable<Signature>{

	private static final long serialVersionUID = 1L;

	@Override
	public Class<Signature> getPersistableClass() {
		return Signature.class;
	}
	
	private Date creationDate;
	
	private Date expirationDate;
	
	private String personUuid;
	
	@ManyToOne
	@JoinColumn(name="documentUuid")
	private Document document;
	
	private String signatureTrackingId;
	
	public Date getCreationDate() {
		return creationDate;
	}
	public Document getDocument() {
		return document;
	}
	public Date getExpirationDate() {
		return expirationDate;
	}
	public String getPersonUuid() {
		return personUuid;
	}
	public String getSignatureTrackingId() {
		return signatureTrackingId;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}
	public void setPersonUuid(String personUuid) {
		this.personUuid = personUuid;
	}
	public void setSignatureTrackingId(String signatureTrackingId) {
		this.signatureTrackingId = signatureTrackingId;
	}

	
	public int compareTo(Signature o) {
		
		int res = this.document.getUuid().compareTo(o.document.getUuid());
		if(res == 0)
		{
			return this.creationDate.compareTo(o.creationDate);	
		}else{
			return res;
		}
	};
	
}
