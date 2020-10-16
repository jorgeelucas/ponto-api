package br.com.pontoapi.exception;

public class HoraInvalidaException extends RuntimeException implements ParametrizedException {
	private static final long serialVersionUID = 1L;

	private String[] params;
	
	public HoraInvalidaException(String msg) {
		super(msg);
	}
	
	public HoraInvalidaException(String msg, String...params) {
		super(msg);
		this.params = params;
	}
	
	public HoraInvalidaException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public String[] getParams() {
        return params;
    }
	
	
}
