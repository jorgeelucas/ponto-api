package br.com.pontoapi.enums;

public enum TipoRegistroEnum {

	ENTRADA("E", "Entrada"),
	INTERVALO_IDA("I", "Saída para o intervalo"),
	INTERVALO_VOLTA("V", "Volta do intervalo"),
	SAIDA("S", "Saída");
	
	private String tipo;
	private String desc;
	
	TipoRegistroEnum(String tipo, String desc){
		this.tipo = tipo;
		this.desc = desc;
	}
	
	public String getTipo() {
		return this.tipo;
	}
	
	public String getDesc() {
		return this.desc;
	}
	
}
