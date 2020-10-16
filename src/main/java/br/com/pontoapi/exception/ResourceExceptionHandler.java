package br.com.pontoapi.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.pontoapi.config.Messages;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@Autowired
	private Messages messages;

	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardException> objectNotFound(ObjectNotFoundException e, HttpServletRequest req) {
		String mensagemErro = extractErrorMessage(e);
		StandardException err = new StandardException(HttpStatus.NOT_FOUND.value(), mensagemErro, System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
	@ExceptionHandler(RegistroInvalidoException.class)
	public ResponseEntity<StandardException> registroInvalido(RegistroInvalidoException e, HttpServletRequest req) {
		String mensagemErro = extractErrorMessage(e);
		StandardException err = new StandardException(HttpStatus.BAD_REQUEST.value(), mensagemErro, System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(DataInvalidaException.class)
	public ResponseEntity<StandardException> dataInvalida(DataInvalidaException e, HttpServletRequest req) {
		String mensagemErro = extractErrorMessage(e);
		StandardException err = new StandardException(HttpStatus.BAD_REQUEST.value(), mensagemErro, System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(HoraInvalidaException.class)
	public ResponseEntity<StandardException> horaInvalida(HoraInvalidaException e, HttpServletRequest req) {
		String mensagemErro = extractErrorMessage(e);
		StandardException err = new StandardException(HttpStatus.BAD_REQUEST.value(), mensagemErro, System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	@ExceptionHandler(MesInvalidoException.class)
	public ResponseEntity<StandardException> mesInvalido(MesInvalidoException e, HttpServletRequest req) {
		String mensagemErro = extractErrorMessage(e);
		StandardException err = new StandardException(HttpStatus.BAD_REQUEST.value(), mensagemErro, System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}
	
	private String extractErrorMessage(ParametrizedException e) {
		String mensagemErro;
		if (e.getParams()!=null && e.getParams().length > 0) {
			mensagemErro = messages.get(e.getMessage(), e.getParams());
		} else {
			mensagemErro = messages.get(e.getMessage());
		}
		return mensagemErro;
	}

}
