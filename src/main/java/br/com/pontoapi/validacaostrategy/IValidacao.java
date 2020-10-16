package br.com.pontoapi.validacaostrategy;

public interface IValidacao {

	boolean isInvalido();
	
	void throwException();
	
}
