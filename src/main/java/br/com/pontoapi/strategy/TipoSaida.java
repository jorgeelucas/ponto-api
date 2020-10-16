package br.com.pontoapi.strategy;

import java.time.LocalTime;
import java.util.List;

import br.com.pontoapi.domain.Registro;
import br.com.pontoapi.enums.TipoRegistroEnum;
import br.com.pontoapi.validacaostrategy.IValidacao;

public class TipoSaida implements ITipo {
	
	@Override
	public void setar(Registro registro, LocalTime hora) {
		registro.setSaida(hora);
	}

	@Override
	public boolean isTipo(TipoRegistroEnum tipo) {
		return TipoRegistroEnum.SAIDA.equals(tipo);
	}
	
	@Override
	public void validacao(List<IValidacao> validacoes) {
		for (IValidacao validacao : validacoes) {
			if (validacao.isInvalido()) {
				validacao.throwException();
			}
		}
	}
}
