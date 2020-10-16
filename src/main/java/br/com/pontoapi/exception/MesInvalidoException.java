package br.com.pontoapi.exception;

public class MesInvalidoException extends RuntimeException implements ParametrizedException {
	private static final long serialVersionUID = 1L;

	private String[] params;
	
	public MesInvalidoException(String msg) {
		super(msg);
	}
	
	public MesInvalidoException(String msg, String...params) {
		super(msg);
		this.params = params;
	}
	
	public MesInvalidoException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
	public String[] getParams() {
        return params;
    }
	
	
}
