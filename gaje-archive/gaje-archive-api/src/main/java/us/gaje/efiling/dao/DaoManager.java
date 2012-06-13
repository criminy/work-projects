package us.gaje.efiling.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Component;

@Component
public class DaoManager {

	@PersistenceContext
	private EntityManager entityManager;
	
	public <T> Dao<T> get(Class<T> clazz) {
		return new DaoJpaImpl<T>(clazz,entityManager);
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
}
