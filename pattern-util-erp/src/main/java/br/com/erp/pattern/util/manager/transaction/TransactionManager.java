package br.com.erp.pattern.util.manager.transaction;

import br.com.erp.pattern.util.manager.exception.ManagerTransactionException;

public interface TransactionManager {

	void commit() throws ManagerTransactionException;
	void rollback() throws ManagerTransactionException;
	void beginTransaction() throws ManagerTransactionException;

}
