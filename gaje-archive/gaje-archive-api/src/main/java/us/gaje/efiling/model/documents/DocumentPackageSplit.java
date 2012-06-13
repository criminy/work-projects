package us.gaje.efiling.model.documents;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import us.gaje.efiling.model.JpaIdEntity;

/**
 * The specification of how the 
 * document packages are split up into individual documents.
 * 
 * @author artripa
 *
 */
@Entity
@Table( name = "docPageRange" )
public class DocumentPackageSplit extends JpaIdEntity implements Comparable<DocumentPackageSplit>
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int pageRangeStart;
	
	private int pageRangeEnd;
	

	@ManyToOne
	@JoinColumn(name = "documentInstanceCode")
	private DocumentInstance documentInstance;

	public DocumentInstance getDocumentInstance() {
		return documentInstance;
	}
	public void setDocumentInstance(DocumentInstance documentInstance) {
		this.documentInstance = documentInstance;
	}
	
	public int getPageRangeEnd() {
		return pageRangeEnd;
	}
	
	public int getPageRangeStart() {
		return pageRangeStart;
	}
	public void setPageRangeStart(int pageRangeStart) {
		this.pageRangeStart = pageRangeStart;
	}
	
	public void setPageRangeEnd(int pageRangeEnd) {
		this.pageRangeEnd = pageRangeEnd;
	}
	
	@Override
	public int compareTo(DocumentPackageSplit o) {
		return Integer.valueOf(pageRangeStart).compareTo(o.pageRangeStart);
	}
	
}

