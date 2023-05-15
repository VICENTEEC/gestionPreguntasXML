package es.mdef.gestionPreguntasXML.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.gestionPreguntasXML.entidades.Pregunta;

public interface PreguntaRepositorio extends JpaRepository<Pregunta, Long> {
	List<Pregunta> findPreguntaByUsuarioId(Long id);
	List<Pregunta> findPreguntaByFamiliaId(Long id);
	List<Pregunta> findPreguntaByEnunciado(String enunciado);
}

