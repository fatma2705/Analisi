package it.prova.analisi.web.api.exception;

public class NotSamePazienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotSamePazienteException(String msg) {
		super(msg);
	}

}
