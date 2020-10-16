package br.com.pontoapi.exception;

public class ObjectNotFoundException extends RuntimeException implements ParametrizedException{

    private static final long serialVersionUID = 1L;
	private String[] params;

    public ObjectNotFoundException(String msg) {
        super(msg);
    }
    
    public ObjectNotFoundException(String msg, String...params) {
        super(msg);
        this.params = params;
    }

    public ObjectNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

	@Override
	public String[] getParams() {
		return this.params;
	}
    
    

}
