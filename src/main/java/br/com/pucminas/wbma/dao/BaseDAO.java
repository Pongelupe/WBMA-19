package br.com.pucminas.wbma.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.pucminas.wbma.entity.BaseEntity;

/**
 * A base DAO for configuring JPA and defining base operations for Entities
 * 
 * @author pongelupe
 *
 * @param <T>
 */
public class BaseDAO<T extends BaseEntity<?>> {

	protected EntityManager em;
	protected Class<T> clazz;

	public BaseDAO(Class<T> clazz) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("wbma");
		em = entityManagerFactory.createEntityManager();
		this.clazz = clazz;
	}

	protected CriteriaBuilder getCb() {
		return em.getCriteriaBuilder();
	}

	/**
	 * A simple operation to persist a Entity into the database
	 * 
	 * @param target - a Entity
	 * @return
	 */
	public T persist(T target) {
		em.getTransaction().begin();
		em.persist(target);
		em.getTransaction().commit();
		return target;
	}

	/**
	 * A simple operation to persist a List of Entities into the database <br>
	 * It uses a SINGLE transaction
	 * 
	 * @param target
	 * @return
	 */
	public List<T> persistAll(List<T> target) {
		em.getTransaction().begin();
		target.forEach(em::persist);
		em.getTransaction().commit();
		return target;
	}

	/**
	 * 
	 * Finds a entity by it's id
	 * 
	 * @param id
	 * @return
	 */
	public <V> Optional<T> findById(V id) {
		em.getTransaction().begin();
		T entity = em.find(clazz, id);
		em.getTransaction().commit();

		return Optional.ofNullable(entity);
	}

	/**
	 * 
	 * Persists a entity by its not in the db
	 * 
	 * @param id
	 * @return
	 */
	public T persistIfNotExists(T target) {
		return persistIfNotExistsElse(target, t -> {
		});
	}

	/**
	 * 
	 * Persists a entity by its not in the db, if the entity already exists then is
	 * executed a consumer with the given entity
	 * 
	 * @param id
	 * @param then
	 * @return
	 */
	public T persistIfNotExistsElse(T target, Consumer<T> then) {
		em.getTransaction().begin();
		if (notExists(target)) {
			em.persist(target);
		} else {
			then.accept(target);
		}
		em.getTransaction().commit();
		return target;
	}

	/**
	 * Persist all the given entities, only new entries
	 * 
	 * @param target
	 * @return
	 */
	public List<T> persistAllIfNotExists(List<T> target) {
		em.getTransaction().begin();
		target.stream().filter(this::notExists).forEach(em::persist);
		em.getTransaction().commit();
		return target;
	}

	/**
	 * It updates a existing entity
	 * 
	 * @param target
	 * @return
	 */
	public T update(T target) {
		em.getTransaction().begin();
		em.merge(target);
		em.getTransaction().commit();
		return target;
	}

	public List<T> findAll() {
		CriteriaQuery<T> cq = getCb().createQuery(clazz);
		Root<T> from = cq.from(clazz);

		cq.select(from);

		return em.createQuery(cq).getResultList();
	}

	/**
	 * 
	 * It checks if the given Entity exists by its id
	 * 
	 * {@link BaseEntity#getId()}
	 * 
	 * @param entity
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public boolean exists(T entity) {
		CriteriaQuery<Integer> cq = getCb().createQuery(Integer.class);
		Root<? extends BaseEntity> from = cq.from(entity.getClass());

		cq.select(getCb().literal(1)).where(getCb().equal(from.get("id"), entity.getId()));

		return !em.createQuery(cq).getResultList().isEmpty();
	}

	/**
	 * 
	 * It checks if the given Entity does not exist by its id
	 * 
	 * 
	 * @see {@link BaseDAO#exists}
	 * 
	 * @param entity
	 * @return
	 */
	public boolean notExists(T entity) {
		return !exists(entity);
	}
}
