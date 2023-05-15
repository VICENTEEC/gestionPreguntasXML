package es.mdef.gestionPreguntasXML.REST;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.gestionPreguntasXML.GestionPreguntasApplication;
import es.mdef.gestionPreguntasXML.entidades.Pregunta;
import es.mdef.gestionPreguntasXML.repositorios.PreguntaRepositorio;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/preguntas")
public class PreguntaController {
	private final PreguntaRepositorio repositorio;
	private final PreguntaAssembler assembler;
	private final PreguntaListaAssembler listaAssembler;
	private final PreguntaPostAssembler postAssembler;
	private final Logger log;

	public PreguntaController(PreguntaRepositorio repositorio, PreguntaAssembler assembler,
			PreguntaPostAssembler postAssembler, PreguntaListaAssembler listaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.postAssembler = postAssembler;
		log = GestionPreguntasApplication.log;
	}

	
	@GetMapping("{id}")
	public PreguntaModel one(@PathVariable Long id) {
		Pregunta pregunta = repositorio.findById(id).orElseThrow(() -> new RegisterNotFoundException(id, "pregunta"));
		log.info("Recuperado " + pregunta);
		return assembler.toModel(pregunta);
	}
	
	@GetMapping
	public CollectionModel<PreguntaListaModel> all() {
		log.info("llegaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@PutMapping("{id}")
	public PreguntaModel edit(@PathVariable Long id, @RequestBody PreguntaPostModel model) {
//		System.err.println(model);
		Pregunta pregunta = repositorio.findById(id).map(prg -> {
			prg.setEnunciado(model.getEnunciado());
			prg.setUsuario(model.getUsuario());
			prg.setFamilia(model.getFamilia());
			return repositorio.save(prg);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "pregunta"));
		log.info("Actualizada " + pregunta);
		return assembler.toModel(pregunta);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrada pregunta " + id);
		repositorio.deleteById(id);
	}
		
	@PostMapping
	public PreguntaModel add(@Valid @RequestBody PreguntaPostModel model) {
		Pregunta pregunta = repositorio.save(postAssembler.toEntity(model));
		log.info("Añadida" + pregunta);
		return assembler.toModel(pregunta);
	}
	
	
}
