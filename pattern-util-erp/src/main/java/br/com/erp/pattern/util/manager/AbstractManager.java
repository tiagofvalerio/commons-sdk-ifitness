package br.com.erp.pattern.util.manager;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Resource;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;

import br.com.erp.pattern.util.dao.GenericDAO;
import br.com.erp.pattern.util.dao.exception.DAOException;
import br.com.erp.pattern.util.manager.exception.ManagementException;
import br.com.erp.pattern.util.manager.exception.ManagerTransactionException;

public abstract class AbstractManager<T, PK extends Serializable> implements
		Manager<T, PK> {
	
	private static final Logger logger = Logger.getLogger(AbstractManager.class);
	
	@Resource
	private UserTransaction transaction;

	public void commit() throws ManagerTransactionException {
		try {
			logger.info("Commiting transaction [BEGIN]");
			transaction.commit();
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (IllegalStateException e) {
			throw new ManagerTransactionException(e);
		} catch (RollbackException e) {
			throw new ManagerTransactionException(e);
		} catch (HeuristicMixedException e) {
			throw new ManagerTransactionException(e);
		} catch (HeuristicRollbackException e) {
			throw new ManagerTransactionException(e);
		} catch (SystemException e) {
			throw new RuntimeException(e);
		} finally {
			logger.info("Commiting transaction [END]");
		}
	}

	public void rollback() throws ManagerTransactionException {
		try {
			logger.info("Rollback transaction [BEGIN]");
			transaction.rollback();
		} catch (IllegalStateException e) {
			throw new ManagerTransactionException(e);
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (SystemException e) {
			throw new RuntimeException(e);
		} finally {
			logger.info("Rolling back transaction [END]");
		}
	}

	public void beginTransaction() throws ManagerTransactionException {
		try {
			logger.info("Entering transaction [BEGIN]");
			transaction.begin();
		} catch (NotSupportedException e) {
			throw new ManagerTransactionException(e);
		} catch (SystemException e) {
			throw new RuntimeException(e);
		}finally {
			logger.info("done begin transaction [END]");
		}
	}

	@Override
	public void save(T obj) throws ManagementException {
		try {
			logger.info("Saving " + obj.getClass().getSimpleName() + " into database");
			getDAO().save(obj);
		} catch (DAOException e) {
			throw new ManagementException(e);
		} catch (NullPointerException e) {
			throw new ManagementException(e);
		} finally {
			logger.info("Object saved into database...");
		}
	}

	@Override
	public T findById(PK id) throws ManagementException {

		try {
			return getDAO().findById(id);
		} catch (DAOException e) {
			throw new ManagementException(e);
		}

	}

	@Override
	public void update(T obj) throws ManagementException {
		try {
			getDAO().update(obj);
		} catch (DAOException e) {
			throw new ManagementException(e);
		}
	}

	@Override
	public void delete(T obj) throws ManagementException {
		try {
			getDAO().delete(obj);
		} catch (DAOException e) {
			throw new ManagementException(e);
		}
	}

	@Override
	public List<T> findAll() throws ManagementException {
		try {
			return getDAO().findAll();
		} catch (DAOException e) {
			throw new ManagementException(e);
		}
	}
	
	@Override
	public List<T> find(Long first, Long last) throws ManagementException {
		
		try {
			return getDAO().find(first, last);
		} catch (DAOException e) {
			throw new ManagementException(e);
		}
	}

	@Override
	public Long listCount() throws ManagementException {
		try {
			return getDAO().listCount();
		} catch (DAOException e) {
			logger.error("Error:", e);
			throw new ManagementException(e);
		}
	}

	protected abstract GenericDAO<T, PK> getDAO();
	

}
