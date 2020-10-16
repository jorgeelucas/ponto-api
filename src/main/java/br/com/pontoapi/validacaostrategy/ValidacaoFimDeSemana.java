package br.com.pontoapi.validacaostrategy;

import java.time.LocalDate;

import br.com.pontoapi.exception.DataInvalidaException;
import br.com.pontoapi.utils.DataUtils;

public class ValidacaoFimDeSemana implements IValidacao {

	private LocalDate data;
	
	public ValidacaoFimDeSemana(LocalDate data) {
		this.data = data;
	}
	
	@Override
	public boolean isInvalido() {
		return DataUtils.isFimDeSemana(data);
	}

	@Override
	public void throwException() {
		throw new DataInvalidaException("final.de.semana");
	}

}
