package br.com.pontoapi.dto;

import java.io.Serializable;

public class RelatorioFuncionarioDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Double horasQueDeveriamSerTrabalhadas;
	private Double horasTrabalhadas;
	private Double saldo;

	public Double getHorasQueDeveriamSerTrabalhadas() {
		return horasQueDeveriamSerTrabalhadas;
	}

	public void setHorasQueDeveriamSerTrabalhadas(Double horasQueDeveriamSerTrabalhadas) {
		this.horasQueDeveriamSerTrabalhadas = horasQueDeveriamSerTrabalhadas;
	}

	public Double getHorasTrabalhadas() {
		return horasTrabalhadas;
	}

	public void setHorasTrabalhadas(Double horasTrabalhadas) {
		this.horasTrabalhadas = horasTrabalhadas;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

}
