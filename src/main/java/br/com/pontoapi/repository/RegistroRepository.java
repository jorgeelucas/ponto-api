package br.com.pontoapi.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pontoapi.domain.Registro;

public interface RegistroRepository extends JpaRepository<Registro, Integer> {

	Optional<Registro> findByDataAndMatriculaFuncionario(LocalDate data, String matriculaFuncionario);

	List<Registro> findByDataBetweenAndMatriculaFuncionario(LocalDate start, LocalDate end, String matricula);

	List<Registro> findAllByMatriculaFuncionario(String matricula);
}
