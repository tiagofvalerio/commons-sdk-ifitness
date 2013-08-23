package br.com.erp.pattern.util.dao.jpa;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import br.com.erp.pattern.util.dao.GenericDAO;
import br.com.erp.pattern.util.dao.exception.DAOException;

/**
 * Implementação GenericDAOPattern para o JPA
 * 
 * @author Tiago F. Valério
 * 
 * @param <T>
 *            a Classe
 * @param <PK>
 *            a Classe que representa o id
 */
public abstract class GenericDAOJPA<T, PK extends Serializable> implements
		GenericDAO<T, PK> {

	protected void beginTransaction() {
		try {
			// TODO: Colocar transação quando possível, JTA EntityManager não
			// permite pegar transação
			// getEntityManager().getTransaction().begin();
		} catch (Exception e) {
			logger.log(Level.WARNING, "Cannot begin transaction.", e);
		}
	}

	protected void rollbackTransaction() {
		try {
			// TODO: Colocar transação quando possível, JTA EntityManager não
			// permite pegar transação
			// getEntityManager().getTransaction().rollback();
		} catch (Exception t) {
			logger.log(Level.SEVERE, "Cannot rollback!", t);
		}
	}

	protected void commitTransaction() {
		try {
			// TODO: Colocar transação quando possível, JTA EntityManager não
			// permite pegar transação
			// getEntityManager().getTransaction().commit();
		} catch (Exception t) {
			logger.log(Level.SEVERE, "Cannot commit!", t);
		}
	}

	private Class<T> classType;
	private static Logger logger = Logger.getLogger("GenericDAOJPA");

	public GenericDAOJPA(Class<T> classType) {
		if (classType == null)
			throw new NullPointerException("ClassType cannot be null");
		this.classType = classType;
	}

	@Override
	public void save(T obj) throws DAOException {
		try {
			beginTransaction();
			getEntityManager().persist(obj);
			getEntityManager().flush();
			commitTransaction();
		} catch (PersistenceException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not save entity [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("] Rolling Back...")
							.toString(), e);
			throw new DAOException(e);
		} catch (NullPointerException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not save entity [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("] Rolling back... ")
							.toString(), e);
			throw new DAOException(e);
		} catch (RuntimeException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not save entity [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("] Rolling Back...")
							.toString(), e);
			throw new DAOException(e);
		} catch (Exception e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not save entity [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("] Rolling Back...")
							.toString(), e);
			throw new DAOException(e);

		}
	}

	@Override
	public T findById(PK id) throws DAOException {

		try {

			T obj = getEntityManager().find(classType, id);

			return obj;

		} catch (NullPointerException e) {
			throw new DAOException(e);
		} catch (RuntimeException e) {
			throw new DAOException(e);
		} catch (Exception e) {
			throw new DAOException(e);
		}

	}

	@Override
	public void update(T obj) throws DAOException {

		try {
			logger.log(Level.INFO,
					"Updating object: " + classType.getSimpleName());
			beginTransaction();
			obj = getEntityManager().merge(obj);
			getEntityManager().flush();
			commitTransaction();
			logger.log(Level.INFO, "Update commited");
		} catch (PersistenceException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Could not update entity [").append(
							obj != null ? obj.getClass().getName() : "NULL")
							.toString(), e);
			throw new DAOException(e);
		} catch (RuntimeException e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Something wrong occured [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("]").toString(), e);
			throw new DAOException(e);
		} catch (Exception e) {
			rollbackTransaction();
			logger.log(
					Level.SEVERE,
					new StringBuilder("Something wrong occured [")
							.append(obj != null ? obj.getClass().getName()
									: "NULL").append("]").toString(), e);
			throw new DAOException(e);
		} finally {
			logger.log(Level.FINE, "Update done");
		}
	}

	@Override
	public void delete(T obj) throws DAOException {
		try {
			logger.log(Level.INFO, "Deleting object...");
			beginTransaction();
			obj = getEntityManager().merge(obj);
			getEntityManager().remove(obj);
			getEntityManager().flush();
			commitTransaction();
		} catch (PersistenceException e) {
			rollbackTransaction();
			throw new DAOException(e);
		} catch (NullPointerException e) {
			rollbackTransaction();
			throw new DAOException(e);
		} catch (RuntimeException e) {
			rollbackTransaction();
			throw new DAOException(e);
		} catch (Exception e) {
			rollbackTransaction();
			throw new DAOException(e);
		}
	}

	@Override
	public List<T> findAll() throws DAOException {
		try {
			CriteriaBuilder criteriaBuilder = getEntityManager()
					.getCriteriaBuilder();
			CriteriaQuery<T> criteriaQuery = criteriaBuilder
					.createQuery(classType);
			Root<T> entity = criteriaQuery.from(classType);
			criteriaQuery.select(entity);
			return getEntityManager().createQuery(criteriaQuery)
					.getResultList();
		} catch (PersistenceException e) {
			logger.log(Level.SEVERE, new StringBuilder(
					"Could not get entities. ").toString(), e);
			throw new DAOException(e);

		} catch (NullPointerException e) {
			logger.log(Level.SEVERE, new StringBuilder(
					"Could not get entities. ").toString(), e);
			throw new DAOException(e);
		} catch (RuntimeException e) {
			logger.log(Level.SEVERE, new StringBuilder(
					"Could not get entities. ").toString(), e);
			throw new DAOException(e);
		} catch (Exception e) {
			logger.log(Level.SEVERE, new StringBuilder(
					"Could not get entities. ").toString(), e);
			throw new DAOException(e);
		}
	}

	@Override
	public List<T> find(Long first, Long last) throws DAOException {

		CriteriaBuilder criteriaBuilder = getEntityManager()
				.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(classType);
		Root<T> entity = criteriaQuery.from(classType);
		criteriaQuery.select(entity);

		TypedQuery<T> q = getEntityManager().createQuery(criteriaQuery);

		q.setFirstResult(first.intValue());
		q.setMaxResults((int) ((last - first) + 1));

		return q.getResultList();
	}

	@Override
	public Long listCount() throws DAOException {

		CriteriaBuilder builder = getEntityManager().getCriteriaBuilder();
		CriteriaQuery<Long> query = builder.createQuery(Long.class);
		Root<T> entity = query.from(classType);

		query.select(builder.count(entity));
		return getEntityManager().createQuery(query).getSingleResult();
	}

	public abstract EntityManager getEntityManager();

}
