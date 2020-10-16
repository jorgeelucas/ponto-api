package br.com.pontoapi.exception;

public class RegistroInvalidoException extends RuntimeException implements ParametrizedException {

    private static final long serialVersionUID = 1L;

    private String[] params;
    
    public RegistroInvalidoException(String msg) {
        super(msg);
    }

    public RegistroInvalidoException(String msg, String...params) {
        super(msg);
        this.params = params;
    }
    
    public RegistroInvalidoException(String msg, Throwable cause) {
        super(msg, cause);
    }

	@Override
	public String[] getParams() {
		return params;
	}

}
