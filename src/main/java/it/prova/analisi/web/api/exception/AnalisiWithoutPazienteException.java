package it.prova.analisi.web.api.exception;

public class AnalisiWithoutPazienteException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public AnalisiWithoutPazienteException(String msg) {
		super(msg);
	}

}
