package br.com.pontoapi.validacaostrategy;

import java.time.LocalTime;

import br.com.pontoapi.domain.Registro;
import br.com.pontoapi.enums.TipoRegistroEnum;
import br.com.pontoapi.exception.HoraInvalidaException;

public class ValidacaoEntradaMaiorQueSaida implements IValidacao {

	private Registro registro;
	private LocalTime nova;
	private TipoRegistroEnum tipo;
	
	public ValidacaoEntradaMaiorQueSaida(Registro registro, TipoRegistroEnum tipo, LocalTime nova) {
		this.registro = registro;
		this.nova = nova;
		this.tipo = tipo;
	}
	
	@Override
	public boolean isInvalido() {
		LocalTime hora = nova;
		if (TipoRegistroEnum.SAIDA.equals(tipo) && registro.getEntrada() != null) {
			hora = registro.getEntrada();
		} else if (TipoRegistroEnum.INTERVALO_VOLTA.equals(tipo) && registro.getIda() != null) {
			hora = registro.getIda();
		}
		return nova.isBefore(hora);
	}
	
	@Override
	public void throwException() {
		throw new HoraInvalidaException("hora.entrada.maior.que.saida");
	}

}
