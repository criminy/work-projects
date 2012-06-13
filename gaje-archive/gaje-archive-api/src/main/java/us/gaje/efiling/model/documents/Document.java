package us.gaje.efiling.model.documents;

import java.io.File;
import java.io.FileInputStream;
import java.util.SortedSet;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;

import us.gaje.efiling.model.CaseRecord;
import us.gaje.efiling.model.JpaIdEntity;
import us.gaje.efiling.model.Persistable;
import us.gaje.efiling.model.Signature;
import us.gaje.efiling.model.enums.DocumentStatus;

/**
 * Object representation of a Document,
 * which stores document meta-data in a
 * SQL database and the binary content
 * in the filesystem.
 * 
 * 
 * @author artripa
 *
 */
@JsonAutoDetect
@Entity
@Configurable
@Table(name = "documents")
public class Document extends JpaIdEntity implements Persistable<Document>,Comparable<Document> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	@JoinColumn(name="documentInstanceCode")
	private DocumentInstance documentInstance;
    
    @Transient
    private static final String DFS_FILESYSTEM_PREFIX = "dfs:/";

    @Transient
    @Value("${us.gaje.database.documents.root}")
    private String documentRoot;

    @Transient
    @Value("${us.gaje.database.documents.dfs.enabled}")
    private boolean dfsSupported;


    private String title;


    @ManyToOne
    @JoinColumn(name = "caseRecordUuid")
    private CaseRecord caseRecord;

    @OneToMany(fetch=FetchType.EAGER)    
    @Sort(type = SortType.NATURAL)
    @OrderBy(value = "version DESC")
    @JoinColumn(name = "metaDataUuid")
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})	
    private List<DocumentVersion> documentVersions;

    @OneToMany(fetch=FetchType.EAGER)
    @Sort(type = SortType.NATURAL)
    @OrderBy(value = "creationDate ASC")
    @JoinColumn(name = "documentUuid")
    private SortedSet<Signature> signatures;
    
    @Column(name="statusCode")
    private int _status;
    
    public int get_status() {
		return _status;
	}
    
    public void set_status(int _status) {
		this._status = _status;
	}
    
    

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public DocumentInstance getDocumentInstance() {
		return documentInstance;
	}
    
    public void setDocumentInstance(DocumentInstance documentInstance) {
		this.documentInstance = documentInstance;
	}
    
    
    public DocumentVersion setContent(byte[] contents) {
      // if (log.isDebugEnabled()) log.debug(String.format("dfs support is %s", this.isDfsSupported() ? "enabled" : "disabled"));
        if (this.getUuid() == null || this.getUuid().equalsIgnoreCase("")) {
            throw new UnsupportedOperationException("Can't call Document.setContent on a non-persisted database object");
        }
        DocumentVersion dv = new DocumentVersion();
        if (this.getDocumentVersions().size() != 0) dv.setVersion(this.getDocumentVersions().get(0).getVersion()); else dv.setVersion(1);
        if (this.isDfsSupported()) dv.setPath(String.format("%s%s_V%s", DFS_FILESYSTEM_PREFIX, this.getUuid(), dv.getVersion())); else dv.setPath(String.format("%s/%s_V%s", this.getDocumentRoot(), this.getUuid(), dv.getVersion()));
        this.documentVersions.add(dv);
        return dv;
    }

    @JsonIgnore
    public byte[] getContent() {
      //  if (log.isDebugEnabled()) log.debug(String.format("dfs support is %s", this.isDfsSupported() ? "enabled" : "disabled"));
        if (this.getUuid() == null || this.getUuid().equalsIgnoreCase("")) {
            throw new UnsupportedOperationException("Can't call Document.getContent on a non-persisted database object");
        }
        try {
            String url = this.getDocumentVersions().get(0).getPath();
            if (url.startsWith(DFS_FILESYSTEM_PREFIX)) {
                url = String.format("%s/%s", this.documentRoot, url.substring(DFS_FILESYSTEM_PREFIX.length()));
            }
       //     if (log.isDebugEnabled()) log.debug(String.format("Url transformed from %x to %x", this.getDocumentVersions().iterator().next().getPath(), url));
            return IOUtils.toByteArray(new FileInputStream(new File(url)));
        } catch (Exception e) {
        //    log.error(e);
        //    log.warn("Couldn't find file for document " + this.getUuid());
            return null;
        }
    }

    public List<DocumentVersion> getDocumentVersions() {
        return documentVersions;
    }

    public void setDocumentVersions(List<DocumentVersion> documentVersions) {
        this.documentVersions = documentVersions;
    }


    public void setCaseRecord(CaseRecord caseRecord) {
        this.caseRecord = caseRecord;
    }

    public CaseRecord getCaseRecord() {
        return caseRecord;
    }

    public String getDocumentRoot() {
        return documentRoot;
    }

    public void setDocumentRoot(String documentRoot) {
        this.documentRoot = documentRoot;
    }

    public void setDfsSupported(boolean dfsSupported) {
        this.dfsSupported = dfsSupported;
    }

    public boolean isDfsSupported() {
        return dfsSupported;
    }

    public SortedSet<Signature> getSignatures() {
        return signatures;
    }

    public void setSignatures(SortedSet<Signature> signatures) {
        this.signatures = signatures;
    }
    
    
    
    @JsonIgnore
    @Transient
    public DocumentStatus getStatus() {
		return DocumentStatus.fromValue(this._status);
	}
    public void setStatus(DocumentStatus status) {
		this._status = status.getValue();
	}
    
	@Override
	public int compareTo(Document o) {
		if(this.caseRecord.getUuid().equals(o.caseRecord.getUuid()))
		{			
			//TODO: compare by creationDate and statusCode
			return this.getTitle().compareTo(o.getTitle());
		}
		return this.getUuid().compareTo(this.getUuid());		
	}
	
    public java.lang.Class<Document> getPersistableClass() {
        return Document.class;
    }

}
