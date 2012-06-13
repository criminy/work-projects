package us.gaje.efiling.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Root;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DaoJpaImpl<T> 
    implements Dao<T> {

	Logger log = Logger.getLogger(DaoJpaImpl.class);
	
    protected Class<T> entityClass;

    @PersistenceContext
    protected EntityManager entityManager;
   
	public DaoJpaImpl(Class<T> clazz, EntityManager entityManager) {
    	this.entityManager = entityManager;    	                  
        this.entityClass = clazz;
    }

    @Override
    public T create(T t) {
        this.entityManager.persist(t);
       // this.entityManager.flush();
        return t;
    }	

    @Override
    public T read(Serializable id) {
        return this.entityManager.find(entityClass, id);
    }

    @Override
    public T update(T t) {
        T tx = this.entityManager.merge(t);
        this.entityManager.flush();
        return tx;
    }

    @Override
    public void delete(T t) {
       t = this.entityManager.merge(t);
        this.entityManager.remove(t);
     //  this.entityManager.flush();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<T> named(String name, Pair<String, ?>... params) {
    	Query q = this.entityManager.createNamedQuery(name);
    	
    	for(Pair<String,?> p : params)
    	{
    		q.setParameter(p.getK(),p.getV());
    	}
    	return q.getResultList();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public List<T> all() {
    	javax.persistence.metamodel.Metamodel metaModel = 
    		entityManager.getMetamodel();
    	String name = metaModel.entity(entityClass).getName();    
    	return entityManager.createQuery("from " + name).getResultList();
    }
    
    @Override
    public List<T> byAttribute(Object attr, Object val) {
    	CriteriaBuilder cb;
    	CriteriaQuery<T> cq;
    	
    	cb = entityManager.getCriteriaBuilder();
    	cq = cb.createQuery(this.entityClass);
    	Root<T> root = cq.from(this.entityClass);
    	
    	cq.where(cb.equal(root.get((String)attr), val));
    	
    	return entityManager.createQuery(cq).getResultList();
    }
}
