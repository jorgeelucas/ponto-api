package br.com.pontoapi.validacaostrategy;

import br.com.pontoapi.domain.Registro;
import br.com.pontoapi.enums.TipoRegistroEnum;
import br.com.pontoapi.exception.HoraInvalidaException;

public class ValidacaoJaPreenchido implements IValidacao {

	private Registro registro;
	private TipoRegistroEnum tipo;
	
	public ValidacaoJaPreenchido(Registro registro, TipoRegistroEnum tipo) {
		this.registro = registro;
		this.tipo = tipo;
	}
	
	@Override
	public boolean isInvalido() {
		switch (tipo.getTipo()) {
		case "E": return registro.getEntrada() != null;
		case "I": return registro.getIda() != null;
		case "V": return registro.getVolta() != null;
		case "S": return registro.getSaida() != null;
		default: return true;
		}
	}

	@Override
	public void throwException() {
		throw new HoraInvalidaException("registro.existente", tipo.getDesc());
	}

}
