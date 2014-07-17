package br.com.ifitness.pattern.util.manager.exception;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ManagementException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;
	private Class<?> servico;
	private Integer code;
	private Throwable cause;

	private static final Logger logger = Logger
			.getLogger("ManagementException");

	public ManagementException(final String message) {
		this.message = message;
		logger.log(Level.SEVERE, message);
	}

	public ManagementException(final String message, final Integer code) {
		this.message = message;
		this.code = code;
		logger.log(Level.SEVERE, new StringBuilder(message).append(" code=[")
				.append(code).append("]").toString());
	}

	public ManagementException(final String message, final Integer code,
			final Class<?> servico) {
		this.message = message;
		this.code = code;
		this.servico = servico;
		logger.log(
				Level.SEVERE,
				new StringBuilder(message).append(" code=[").append(code)
						.append("] servico=[")
						.append(servico != null ? servico.getName() : "NULL")
						.append("]").toString());
	}

	public ManagementException(final String message, final Integer code,
			final Class<?> servico, final Throwable cause) {
		this.message = message;
		this.code = code;
		this.servico = servico;
		this.cause = cause;
		logger.log(
				Level.SEVERE,
				new StringBuilder(message).append(" code=[").append(code)
						.append("] servico=[")
						.append(servico != null ? servico.getName() : "NULL")
						.append("]").toString(), cause);
	}
	
	public ManagementException(final Throwable cause) {
		this.cause = cause;
	}

	public ManagementException(final String message, final Throwable cause) {
		this.message = message;
		this.cause = cause;
	}

	public String getMessage() {
		return message;
	}

	public Class<?> getServico() {
		return servico;
	}

	public Integer getCode() {
		return code;
	}

	public Throwable getCause() {
		return cause;
	}

}
