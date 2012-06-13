package us.gaje.efiling.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnore;

@JsonAutoDetect
public class Reference<T extends IdEntity> implements Serializable {	
	
	private static final long serialVersionUID = 1L;
	
	@JsonIgnore(true)
	private T t = null;
	private String id;
	
	public Reference() {

	}
	
	public Reference(T t) {
		id = t.getId();
		this.t = t;
	}
	
	@JsonIgnore(true)
	public T getT() {
		return t;
	}

	public String getReferenceId()
	{
		return id;
	}
	
	public void setReferenceId(String id) {
		this.id = id;
	}
	
	
	
	@JsonIgnore(true)
	public void setT(T t) {
		this.t = t;
	}
	

}
