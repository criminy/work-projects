package us.gaje.efiling.model.documents;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;

import us.gaje.efiling.model.JpaIdEntity;
import us.gaje.efiling.model.Persistable;

/**
 * A version of a document binary at a specific time,
 * event.
 * @author artripa
 *
 */
@JsonAutoDetect
@Entity
@Table(name="documentBinaries")
public class DocumentVersion extends JpaIdEntity implements Comparable<DocumentVersion>,Persistable<DocumentVersion> {

	private static final long serialVersionUID = 1L;


	
	//@JsonIgnore(true)
	//@Transient
	//private Logger log = Logger.getLogger(DocumentVersion.class);
	
	private String path;
	
	private int version;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="metaDataUuid")
	private Document document;
	
	public Document getDocument() {
		return document;
	}
	public String getPath() {
		
		return path;
	}
	
	public int getVersion() {
		return version;
	}
	public void setDocument(Document document) {
		this.document = document;
	}
	public void setPath(String path) {
	//	log.debug("Setting path " + path);
		this.path = path;
	}

	public void setVersion(int version) {
		this.version = version;
	}
	
	public int compareTo(DocumentVersion o) {
		
		int res = this.document.getUuid().compareTo(o.document.getUuid());
		if(res == 0)
		{
			Integer i = o.version;
			Integer j = this.version;
			return j.compareTo(i);
		}else{
			return res;
		}
	};
	
	
	@Override
	public Class<DocumentVersion> getPersistableClass() {
		return DocumentVersion.class;
	}
}
