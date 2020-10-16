package br.com.pontoapi.utils;

import java.text.DateFormatSymbols;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import br.com.pontoapi.exception.MesInvalidoException;

public class DataUtils {
	
	private static final int WORKING_HOURS = 8;

	private DataUtils() {
	}

	public static boolean isFimDeSemana(LocalDate data) {
		DayOfWeek sabado = DayOfWeek.SATURDAY;
		DayOfWeek domingo = DayOfWeek.SUNDAY;
		return !isEmpty(data) && (data.getDayOfWeek().equals(sabado) || data.getDayOfWeek().equals(domingo));
	}
	
	public static Double qtdHorasDeveriamSerTrabalhado(int mes) {
		LocalDate periodo = LocalDate.now().withMonth(mes);
		LocalDate primeiro = periodo.withDayOfMonth(1);
		LocalDate ultimo = periodo.withDayOfMonth(periodo.lengthOfMonth());
		
		List<LocalDate> dias = new ArrayList<>();
		
		while (primeiro.isBefore(ultimo) || primeiro.isEqual(ultimo)) {
			if (!isFimDeSemana(primeiro)) {
				dias.add(primeiro);
			}
			primeiro = primeiro.plusDays(1);
		}
		
		return (double) (dias.size() * WORKING_HOURS);
	}
	
	public static Month extrairMes(String mes) {
		DateFormatSymbols symbols = new DateFormatSymbols(Locale.getDefault());
		List<String> listaDeMeses = Arrays.asList(symbols.getMonths());
		listaDeMeses = lowerCase(listaDeMeses);
		int indexOf = listaDeMeses.indexOf(mes.toLowerCase());
		validarMes(mes, indexOf);
		return Month.of(indexOf+1);
	}

	private static List<String> lowerCase(List<String> listaDeMeses) {
		listaDeMeses = listaDeMeses.stream().map(String::toLowerCase).collect(Collectors.toList());
		return listaDeMeses;
	}

	private static void validarMes(String mes, int indexOf) {
		if (indexOf == -1) {
			throw new MesInvalidoException("mes.invalido", mes);
		}
	}
	
	private static boolean isEmpty(LocalDate data) {
		return data == null;
	}

	public static int getMes(String mes) {
		return 0;
	}
}
