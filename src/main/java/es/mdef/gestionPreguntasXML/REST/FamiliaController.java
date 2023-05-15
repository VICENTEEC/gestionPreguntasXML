package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.gestionPreguntasXML.GestionPreguntasApplication;
import es.mdef.gestionPreguntasXML.entidades.FamiliaImpl;
import es.mdef.gestionPreguntasXML.entidades.Pregunta;
import es.mdef.gestionPreguntasXML.entidades.Usuario;
import es.mdef.gestionPreguntasXML.repositorios.FamiliaImplRepositorio;
import es.mdef.gestionPreguntasXML.repositorios.PreguntaRepositorio;
import jakarta.validation.Valid;


@RestController()
@RequestMapping("/familias")
public class FamiliaController {
	private final FamiliaImplRepositorio repositorio;
	private final PreguntaRepositorio preguntaRepositorio;
	private final FamiliaAssembler assembler;
	private final FamiliaListaAssembler listaAssembler;
	private final FamiliaPostAssembler postAssembler;
	private final PreguntaListaAssembler preguntaListaAssembler;
	private final UsuarioListaAssembler usuarioListaAssembler;
	private final Logger log;
	
	FamiliaController(FamiliaImplRepositorio repositorio, PreguntaRepositorio preguntaRepositorio,
			FamiliaAssembler assembler, FamiliaListaAssembler listaAssembler,
			FamiliaPostAssembler postAssembler, PreguntaListaAssembler preguntaListaAssembler,
			UsuarioListaAssembler usuarioListaAssembler) {
		this.repositorio = repositorio;
		this.preguntaRepositorio = preguntaRepositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.postAssembler = postAssembler;
		this.preguntaListaAssembler = preguntaListaAssembler;
		this.usuarioListaAssembler = usuarioListaAssembler;
		this.log = GestionPreguntasApplication.log;
	}
	
	@GetMapping("{id}")
	public FamiliaModel one(@PathVariable Long id) {
		FamiliaImpl familiaImpl = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "familia"));
		log.info("Recuperada " + familiaImpl);
		return assembler.toModel(familiaImpl);
	}
	
	@PutMapping("{id}")
	public FamiliaModel edit(@PathVariable Long id, @RequestBody FamiliaPostModel model) {
		FamiliaImpl familiaImpl = repositorio.findById(id).map(fam -> {
			fam.setEnunciado(model.getEnunciado());
			return repositorio.save(fam);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "familia"));
		log.info("Actualizada " + familiaImpl);
		return assembler.toModel(familiaImpl);
	}
	
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrada familia " + id);
		repositorio.deleteById(id);
	}
	
	@GetMapping
	public CollectionModel<FamiliaListaModel> all() {
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	@GetMapping("{id}/preguntas")
	public CollectionModel<PreguntaListaModel> preguntasEnFamilias(@PathVariable Long id) {
		List<Pregunta> preguntas = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "familia"))     ////////////////////////////////////////////////////TO MODEL
				.getPreguntas();
		return CollectionModel.of(
				preguntas.stream().map(pregunta -> preguntaListaAssembler.toModel(pregunta)).collect(Collectors.toList()),     //////////TO COLLECTION
				linkTo(methodOn(FamiliaController.class).preguntasEnFamilias(id)).withSelfRel()///////(methodOn(FamiliaController.class): ESTO COGE LO DE ARRIBA: @RequestMapping("/familias")
				);                                                                             /////// preguntasEnFamilias(id)  : ESTO COGE LO DE AQUI: @GetMapping("{id}/preguntas")       
	}
	
	@GetMapping("{id}/usuarios")
	public CollectionModel<UsuarioListaModel> usuariosEnFamilias(@PathVariable Long id) {
		   FamiliaImpl familiaImpl = repositorio.findById(id)
		            .orElseThrow(() -> new RegisterNotFoundException(id, "familia"));

		    List<Usuario> usuarios = familiaImpl.getPreguntas().stream()
		            .map(Pregunta::getUsuario)
		            .distinct()
		            .collect(Collectors.toList());

		    return CollectionModel.of(
					usuarios.stream().map(usuario -> usuarioListaAssembler.toModel(usuario)).collect(Collectors.toList()),     //////////TO COLLECTION
					linkTo(methodOn(FamiliaController.class).usuariosEnFamilias(id)).withSelfRel()///////(methodOn(FamiliaController.class): ESTO COGE LO DE ARRIBA: @RequestMapping("/familias")
					); 
	}
	
	@PostMapping
	public FamiliaModel add(@Valid @RequestBody FamiliaPostModel model) {
		FamiliaImpl familiaImpl = repositorio.save(postAssembler.toEntity(model));
		log.info("Añadida " + familiaImpl);
		return assembler.toModel(familiaImpl);
	}

}
