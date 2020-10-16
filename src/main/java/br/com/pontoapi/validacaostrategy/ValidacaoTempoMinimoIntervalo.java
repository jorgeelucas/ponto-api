package br.com.pontoapi.validacaostrategy;

import java.time.Duration;
import java.time.LocalTime;

import br.com.pontoapi.domain.Registro;
import br.com.pontoapi.enums.TipoRegistroEnum;
import br.com.pontoapi.exception.HoraInvalidaException;

public class ValidacaoTempoMinimoIntervalo implements IValidacao {

	private Registro registro;
	private LocalTime nova;
	private TipoRegistroEnum tipo;
	
	public ValidacaoTempoMinimoIntervalo(Registro registro, TipoRegistroEnum tipo, LocalTime nova) {
		this.registro = registro;
		this.nova = nova;
		this.tipo = tipo;
	}
	
	@Override
	public boolean isInvalido() {
		Duration duration = Duration.ofHours(1);
		if (TipoRegistroEnum.INTERVALO_IDA.equals(tipo) && registro.getVolta() != null) {
			duration = Duration.between(nova, registro.getVolta());
		} else if (TipoRegistroEnum.INTERVALO_VOLTA.equals(tipo) && registro.getIda() != null) {
			duration = Duration.between(registro.getIda(), nova);
		}
		return duration.toHours() < 1;
	}
	
	@Override
	public void throwException() {
		throw new HoraInvalidaException("periodo.minimo.intevalo");
	}

}
