package br.com.ifitness.pattern.util.manager.exception;

import org.apache.log4j.Logger;

public class ManagerTransactionException extends Exception {

	private static final long serialVersionUID = 1L;
	private Logger logger = Logger.getLogger("ManagerTransactionException");

	public ManagerTransactionException() {
		logger.error(new StringBuilder("ManagerTransactionException was trown"));
	}

	public ManagerTransactionException(String message) {
		super(message);
		logger.error(new StringBuilder("ManagerTransactionException was trown [").append(message).append("]"));
		
	}

	public ManagerTransactionException(Throwable cause) {
		super(cause);
		logger.error(new StringBuilder("ManagerTransaction was thrown [").append(cause.getMessage()).append("]"), cause);
	}

	public ManagerTransactionException(String message, Throwable cause) {
		super(message, cause);
		logger.error(new StringBuilder("ManagerTransaction was thrown [").append(message).append("]"), cause);
	}

}
