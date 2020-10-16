package br.com.pontoapi.strategy;

import java.util.Arrays;
import java.util.List;

public class TipoFactory {

	public static List<ITipo> build() {
		return Arrays.asList(
				new TipoEntrada(),
				new TipoIda(),
				new TipoVolta(),
				new TipoSaida()
				);
	}
	
}
