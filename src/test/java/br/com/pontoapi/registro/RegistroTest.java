package br.com.pontoapi.registro;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.pontoapi.ApiPontoApplication;
import br.com.pontoapi.domain.Registro;
import br.com.pontoapi.dto.AtualizacaoDTO;
import br.com.pontoapi.dto.CadastroRegistroDTO;
import br.com.pontoapi.enums.TipoRegistroEnum;
import br.com.pontoapi.repository.RegistroRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiPontoApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class RegistroTest {
	
	private MockMvc mockMvc;
	
	@MockBean
	private RegistroRepository registroRepository;
	
	@Autowired
	private WebApplicationContext webAppCtx;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private CadastroRegistroDTO cadastroDto;
	private Registro registro;
	private Registro registroRetorno;
	
	@Before
	public void setUp() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppCtx).build();
		
		registro = new Registro();
		registro.setData(LocalDate.now());
		registro.setMatriculaFuncionario("2");
		
		registroRetorno = new Registro();
		registroRetorno.setId(1);
		registroRetorno.setMatriculaFuncionario("1");
		registroRetorno.setData(LocalDate.now());
		
		cadastroDto = new CadastroRegistroDTO();
		cadastroDto.setData(LocalDate.now());
		cadastroDto.setTipoRegistro(TipoRegistroEnum.ENTRADA);
		cadastroDto.setHora(LocalTime.of(8, 0));
		cadastroDto.setMatriculaFuncionario("1");
		
		when(registroRepository.save(ArgumentMatchers.any(Registro.class))).thenReturn(registroRetorno);
		
	}
	
	@Test
	public void testaCriarNovoRegistro() throws Exception {
		when(registroRepository.findByDataAndMatriculaFuncionario(Mockito.any(LocalDate.class), Mockito.anyString()))
				.thenReturn(Optional.ofNullable(null));
		
		mockMvc.perform(post("/api/v1/registros")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(cadastroDto)))
		.andExpect(status().isCreated());
	}
	
	@Test
	public void testaCriarNovoRegistroJaCadastrado() throws Exception {
		this.cadastroDto.setHora(LocalTime.of(9, 0));
		this.cadastroDto.setTipoRegistro(TipoRegistroEnum.ENTRADA);
		
		this.registroRetorno.setEntrada(LocalTime.of(8, 0));
		when(registroRepository.findByDataAndMatriculaFuncionario(ArgumentMatchers.any(LocalDate.class),
				ArgumentMatchers.anyString())).thenReturn(Optional.of(this.registroRetorno));

		mockMvc.perform(post("/api/v1/registros")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(cadastroDto)))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testaCriarNovoRegistroComSaidaMaiorQueEntrada() throws Exception {
		this.cadastroDto.setHora(LocalTime.of(8, 0));
		this.cadastroDto.setTipoRegistro(TipoRegistroEnum.SAIDA);
		
		this.registroRetorno.setEntrada(LocalTime.of(23, 0));
		when(registroRepository.findByDataAndMatriculaFuncionario(ArgumentMatchers.any(LocalDate.class),
				ArgumentMatchers.anyString())).thenReturn(Optional.of(this.registroRetorno));

		mockMvc.perform(post("/api/v1/registros")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(cadastroDto)))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testaCriarNovoRegistroComSaidaMaiorQueEntradaIntervalo() throws Exception {
		this.cadastroDto.setHora(LocalTime.of(11, 0));
		this.cadastroDto.setTipoRegistro(TipoRegistroEnum.INTERVALO_VOLTA);
		
		this.registroRetorno.setIda(LocalTime.of(12, 0));
		when(registroRepository.findByDataAndMatriculaFuncionario(ArgumentMatchers.any(LocalDate.class),
				ArgumentMatchers.anyString())).thenReturn(Optional.of(this.registroRetorno));

		mockMvc.perform(post("/api/v1/registros")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(cadastroDto)))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testaCriarNovoRegistroComPeloMenosUmaHoraDeIntervalo() throws Exception {
		this.cadastroDto.setHora(LocalTime.of(12, 59));
		this.cadastroDto.setTipoRegistro(TipoRegistroEnum.INTERVALO_VOLTA);
		
		this.registroRetorno.setIda(LocalTime.of(12, 0));
		when(registroRepository.findByDataAndMatriculaFuncionario(ArgumentMatchers.any(LocalDate.class),
				ArgumentMatchers.anyString())).thenReturn(Optional.of(this.registroRetorno));

		mockMvc.perform(post("/api/v1/registros")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(cadastroDto)))
		.andExpect(status().is4xxClientError());
		
		this.cadastroDto.setHora(LocalTime.of(12, 00));
		this.cadastroDto.setTipoRegistro(TipoRegistroEnum.INTERVALO_IDA);
		
		this.registroRetorno.setVolta(LocalTime.of(12, 59));
		when(registroRepository.findByDataAndMatriculaFuncionario(ArgumentMatchers.any(LocalDate.class),
				ArgumentMatchers.anyString())).thenReturn(Optional.of(this.registroRetorno));

		mockMvc.perform(post("/api/v1/registros")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(cadastroDto)))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testaAlterarRegistro() throws Exception {
		AtualizacaoDTO dto = new AtualizacaoDTO();
		dto.setHora(LocalTime.of(8, 30));
		dto.setTipoRegistro(TipoRegistroEnum.ENTRADA);
		
		Registro registroParaAlterar = new Registro();
		
		when(registroRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(registroParaAlterar));
		
		Integer id = 1;
		mockMvc.perform(put("/api/v1/registros/" + id)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().isOk());
	}
	
	@Test
	public void testaAlterarRegistroComSaidaMenorQueEntrada() throws Exception {
		AtualizacaoDTO dto = new AtualizacaoDTO();
		dto.setHora(LocalTime.of(12, 0));
		dto.setTipoRegistro(TipoRegistroEnum.SAIDA);
		
		Registro registroParaAlterar = new Registro();
		registroParaAlterar.setEntrada(LocalTime.of(15, 0));
		
		when(registroRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(registroParaAlterar));
		
		Integer id = 1;
		mockMvc.perform(put("/api/v1/registros/" + id)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().is4xxClientError());
	}
	
	@Test
	public void testaAlterarRegistroComPeriodoMinimoIntervaloNaoAtingido() throws Exception {
		AtualizacaoDTO dto = new AtualizacaoDTO();
		dto.setHora(LocalTime.of(12, 1));
		dto.setTipoRegistro(TipoRegistroEnum.INTERVALO_IDA);
		
		Registro registroParaAlterar = new Registro();
		registroParaAlterar.setVolta(LocalTime.of(13, 0));
		
		when(registroRepository.findById(ArgumentMatchers.anyInt())).thenReturn(Optional.of(registroParaAlterar));
		
		Integer id = 1;
		mockMvc.perform(put("/api/v1/registros/" + id)
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(dto)))
		.andExpect(status().is4xxClientError());
	}

}
