package it.prova.analisi.web.api.exception;

public class IdNotNullForInsertException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public IdNotNullForInsertException(String msg) {
		super(msg);
	}
}
