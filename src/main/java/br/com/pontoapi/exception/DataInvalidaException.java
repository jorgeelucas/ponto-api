package br.com.pontoapi.exception;

public class DataInvalidaException extends RuntimeException implements ParametrizedException {
	private static final long serialVersionUID = 1L;
	
	private String[] params;

	public DataInvalidaException(String msg) {
		super(msg);
	}
	
	public DataInvalidaException(String msg, String...params) {
		super(msg);
		this.params = params;
	}
	
	public DataInvalidaException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public String[] getParams() {
		return params;
	}
}
