package br.com.pontoapi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.pontoapi.dto.AtualizacaoDTO;
import br.com.pontoapi.dto.CadastroRegistroDTO;
import br.com.pontoapi.dto.RegistroDTO;
import br.com.pontoapi.dto.RelatorioFuncionarioDTO;
import br.com.pontoapi.service.RegistroService;
import io.swagger.annotations.Api;

@Api(value = "Registros")
@RestController
@RequestMapping("api/v1/registros")
public class RegistroRestController {
	
	@Autowired
	private RegistroService registroService;
	
	@PostMapping
	public ResponseEntity<Void> createRegistro(@RequestBody CadastroRegistroDTO cadastroDTO) {
		Integer idCreated = registroService.create(cadastroDTO);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(idCreated).toUri();
		return ResponseEntity.created(uri).build();
	}
	
	@GetMapping
	public ResponseEntity<List<RegistroDTO>> getAllRegistrosPorMatricula(@RequestParam(name = "matricula", required = true) String matricula) {
		List<RegistroDTO> registros = registroService.getAllbyMatricula(matricula);
		return ResponseEntity.ok().body(registros);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<RegistroDTO> getById(@PathVariable("id") Integer id) {
		return ResponseEntity.ok().body(registroService.getById(id));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<RegistroDTO> updateRegistro(@RequestBody AtualizacaoDTO atualizarDTO, @PathVariable("id") Integer id) {
		return ResponseEntity.ok().body(registroService.update(atualizarDTO, id));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteRegistro(@PathVariable("id") Integer id) {
		registroService.deletarRegistroPorId(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/relatorio/{matricula}")
	public ResponseEntity<RelatorioFuncionarioDTO> getRegistroById(@PathVariable("matricula") String matricula,
			@RequestParam(value = "mes", required = false) String mes) {
		RelatorioFuncionarioDTO relatorio = registroService.getRelatorio(matricula, mes);
		return ResponseEntity.ok().body(relatorio);
	}
	
}
