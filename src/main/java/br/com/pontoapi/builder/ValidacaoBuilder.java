package br.com.pontoapi.builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import br.com.pontoapi.domain.Registro;
import br.com.pontoapi.enums.TipoRegistroEnum;
import br.com.pontoapi.validacaostrategy.IValidacao;
import br.com.pontoapi.validacaostrategy.ValidacaoEntradaMaiorQueSaida;
import br.com.pontoapi.validacaostrategy.ValidacaoFimDeSemana;
import br.com.pontoapi.validacaostrategy.ValidacaoJaPreenchido;
import br.com.pontoapi.validacaostrategy.ValidacaoTempoMinimoIntervalo;

public class ValidacaoBuilder {

	private List<IValidacao> validacoes;
	
	public ValidacaoBuilder() {
		this.validacoes = new ArrayList<>();
	}
	
	public ValidacaoBuilder comJaPreenchido(Registro registro, TipoRegistroEnum tipo) {
		this.validacoes.add(new ValidacaoJaPreenchido(registro, tipo));
		return this;
	}
	
	public ValidacaoBuilder comFimDeSemana(LocalDate data) {
		this.validacoes.add(new ValidacaoFimDeSemana(data));
		return this;
	}
	
	public ValidacaoBuilder comEntradaMaiorQueSaida(Registro registro, TipoRegistroEnum tipo, LocalTime nova) {
		this.validacoes.add(new ValidacaoEntradaMaiorQueSaida(registro, tipo, nova));
		return this;
	}
	
	public ValidacaoBuilder comTempoMinimoIntervalo(Registro registro, TipoRegistroEnum tipo, LocalTime nova) {
		this.validacoes.add(new ValidacaoTempoMinimoIntervalo(registro, tipo, nova));
		return this;
	}
	
	public List<IValidacao> construir() {
		return this.validacoes;
	}
	
}
