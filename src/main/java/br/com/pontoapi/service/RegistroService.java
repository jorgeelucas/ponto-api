package br.com.pontoapi.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pontoapi.builder.ValidacaoBuilder;
import br.com.pontoapi.domain.Registro;
import br.com.pontoapi.dto.AtualizacaoDTO;
import br.com.pontoapi.dto.CadastroRegistroDTO;
import br.com.pontoapi.dto.RegistroDTO;
import br.com.pontoapi.dto.RelatorioFuncionarioDTO;
import br.com.pontoapi.enums.TipoRegistroEnum;
import br.com.pontoapi.exception.ObjectNotFoundException;
import br.com.pontoapi.repository.RegistroRepository;
import br.com.pontoapi.strategy.ITipo;
import br.com.pontoapi.strategy.TipoFactory;
import br.com.pontoapi.utils.Converter;
import br.com.pontoapi.utils.DataUtils;
import br.com.pontoapi.validacaostrategy.IValidacao;

@Service
public class RegistroService {

	@Autowired
	private RegistroRepository registroRepository;
	
	public Integer create(CadastroRegistroDTO cadastro) {
		Registro registro = new Registro();
		
		Optional<Registro> registroOp = registroRepository.findByDataAndMatriculaFuncionario(cadastro.getData(),
				cadastro.getMatriculaFuncionario());
		if (registroOp.isPresent()) {
			registro = registroOp.get();
		}
		
		registro.setData(cadastro.getData());
		registro.setMatriculaFuncionario(cadastro.getMatriculaFuncionario());
		
		List<IValidacao> validacoes = new ValidacaoBuilder()
				.comJaPreenchido(registro, cadastro.getTipoRegistro())
				.comEntradaMaiorQueSaida(registro, cadastro.getTipoRegistro(), cadastro.getHora())
				.comTempoMinimoIntervalo(registro, cadastro.getTipoRegistro(), cadastro.getHora())
				.comFimDeSemana(cadastro.getData()).construir();
		
		setarHoraStrategy(cadastro.getTipoRegistro(), cadastro.getHora(), registro, validacoes);
		
		calcularTotalDoDia(registro);
		
		Registro registroPersistido = registroRepository.save(registro);
		return registroPersistido.getId();
	}
	
	public RegistroDTO getById(Integer id) {
		Registro registro = getEntityById(id);
		return Converter.convert(registro, RegistroDTO.class);
	}
	
	public List<RegistroDTO> getAllbyMatricula(String matricula) {
		List<Registro> registros = registroRepository.findAllByMatriculaFuncionario(matricula);
		return registros.stream().map(entity -> Converter.convert(entity, RegistroDTO.class))
				.collect(Collectors.toList());
	}
	
	public RegistroDTO update(AtualizacaoDTO dto, Integer id) {
		Registro registro = getEntityById(id);
		
		List<IValidacao> validacoes = new ValidacaoBuilder()
			.comEntradaMaiorQueSaida(registro, dto.getTipoRegistro(), dto.getHora())
			.comTempoMinimoIntervalo(registro, dto.getTipoRegistro(), dto.getHora())
			.construir();
		
		setarHoraStrategy(dto.getTipoRegistro(), dto.getHora(), registro, validacoes);
		calcularTotalDoDia(registro);
		return Converter.convert(registroRepository.save(registro), RegistroDTO.class);
	}
	
	public void deletarRegistroPorId(Integer id) {
		getEntityById(id);
		registroRepository.deleteById(id);
	}
	
	public RelatorioFuncionarioDTO getRelatorio(String matricula, String mes) {
		Month month = DataUtils.extrairMes(mes);
		Double qtdHorasDeveriamSerTrabalhado = DataUtils.qtdHorasDeveriamSerTrabalhado(month.getValue());
		
		LocalDate periodo = LocalDate.now().withMonth(month.getValue());
		LocalDate start = periodo.withDayOfMonth(1);
		LocalDate end = periodo.withDayOfMonth(periodo.lengthOfMonth());
		
		List<Registro> registrosDoMes = registroRepository.findByDataBetweenAndMatriculaFuncionario(start, end, matricula);
		
		double total = registrosDoMes.stream().mapToDouble(Registro::getTotalHorasTrabalhadas).sum();
		
		RelatorioFuncionarioDTO relatorio = new RelatorioFuncionarioDTO();
		relatorio.setHorasTrabalhadas(total);
		relatorio.setHorasQueDeveriamSerTrabalhadas(qtdHorasDeveriamSerTrabalhado);
		relatorio.setSaldo(total - qtdHorasDeveriamSerTrabalhado);
		
		return relatorio;
	}

	
	private void calcularTotalDoDia(Registro registro) {
		Double primeiroPeriodo = calcularPeriodo(registro.getEntrada(), registro.getIda());
		Double segundoPeriodo = calcularPeriodo(registro.getVolta(), registro.getSaida());
		registro.setTotalHorasTrabalhadas(Double.sum(primeiroPeriodo, segundoPeriodo));
	}

	private Double calcularPeriodo(LocalTime de, LocalTime ate) {
		Double total = 0.0;
		if (de != null && ate != null) {
			long minutos = ChronoUnit.MINUTES.between(de, ate);
			total = minutos / 60.0;
		}
		return total;
	}

	private void setarHoraStrategy(TipoRegistroEnum tipoRegistro, LocalTime hora, Registro registro, List<IValidacao> validacoes) {
		List<ITipo> tipos = TipoFactory.build();
		tipos.stream()
			.filter(tipo -> tipo.isTipo(tipoRegistro))
			.forEach(tipo -> {
				tipo.validacao(validacoes);
				tipo.setar(registro, hora);
		});
	}
	
	private Registro getEntityById(Integer id) {
		return registroRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException("registro.nao.encontrado", String.valueOf(id)));
	}

}
