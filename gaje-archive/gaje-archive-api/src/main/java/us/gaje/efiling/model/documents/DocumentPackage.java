package us.gaje.efiling.model.documents;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import us.gaje.efiling.model.Court;
import us.gaje.efiling.model.JpaIdEntity;

/**
 * A description of a group of documents which can
 * be treated as one binary in the efiling system.
 * 
 * @author artripa
 *
 */
@Entity
@Table(name="documentPackage")
public class DocumentPackage extends JpaIdEntity{

	private static final long serialVersionUID = 1L;

	private String packageName;

	@ManyToOne
	@JoinColumn(name="courtID")
	private Court court;
	
	private int currentCaseAction;
	  
    @Sort(type = SortType.NATURAL)
    @OrderBy(value = "pageRangeStart asc")
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})	
	@OneToMany
	@JoinColumn(name="documentPackageUuid")
	private Set<DocumentPackageSplit> splits = new HashSet<DocumentPackageSplit>();
	
	
	public Set<DocumentPackageSplit> getSplits() {
		return splits;
	}
	
	public void setSplits(Set<DocumentPackageSplit> splits) {
		this.splits = splits;
	}
	
	
	public int getCurrentCaseAction() {
		return currentCaseAction;
	}
	
	public void setCurrentCaseAction(int currentCaseAction) {
		this.currentCaseAction = currentCaseAction;
	}
	public Court getCourt() {
		return court;
	}
	
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public void setCourt(Court court) {
		this.court = court;
	}
}
