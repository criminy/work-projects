package us.gaje.efiling.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {
    public T create(T t);
    public T read(Serializable id);
    public T update(T t);
    public void delete(T t);
    public List<T> named(String name,Pair<String,?>...  param);
	public List<T> all();
	
	public List<T> byAttribute(Object attr,Object val);
}
