package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionPreguntasXML.entidades.Pregunta;

@Component
public class PreguntaPostAssembler implements RepresentationModelAssembler<Pregunta, PreguntaPostModel>{
	
	public PreguntaPostModel toModel(Pregunta entity) {
		PreguntaPostModel model = new PreguntaPostModel();
		model.setEnunciado(entity.getEnunciado());
		model.setUsuario(entity.getUsuario());
		model.setFamilia(entity.getFamilia());
		model.add(
				linkTo(methodOn(PreguntaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).one(entity.getUsuario().getId())).withRel("usuariodesdePregPA"),
				linkTo(methodOn(FamiliaController.class).one(entity.getFamilia().getId())).withRel("familiadesdePregPA")
				);
		return model;
	}
	
	public Pregunta toEntity(PreguntaPostModel model) {
		Pregunta pregunta = new Pregunta();
		pregunta.setEnunciado(model.getEnunciado());
		pregunta.setUsuario(model.getUsuario());
		pregunta.setFamilia(model.getFamilia());
		return pregunta;
	}
}