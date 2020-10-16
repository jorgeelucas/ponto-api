package br.com.pontoapi.strategy;

import java.time.LocalTime;
import java.util.List;

import br.com.pontoapi.domain.Registro;
import br.com.pontoapi.enums.TipoRegistroEnum;
import br.com.pontoapi.validacaostrategy.IValidacao;

public interface ITipo {
	
	public boolean isTipo(TipoRegistroEnum tipo);
	
	public void validacao(List<IValidacao> validacoes);
	
	public void setar(Registro registro, LocalTime hora);

}
