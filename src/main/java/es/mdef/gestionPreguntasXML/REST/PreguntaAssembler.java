package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionPreguntasXML.entidades.Pregunta;


@Component
public class PreguntaAssembler implements RepresentationModelAssembler <Pregunta, PreguntaModel>{

	@Override
	public PreguntaModel toModel(Pregunta entity) {
		PreguntaModel model = new PreguntaModel();
		model.setEnunciado(entity.getEnunciado());     //////////////////////////////////////////////////////////
		model.add(
				linkTo(methodOn(PreguntaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).one(entity.getUsuario().getId())).withRel("usuarioDesdePreguntaAssembler"),
				linkTo(methodOn(FamiliaController.class).one(entity.getFamilia().getId())).withRel("familiaDesdePreguntaAssembler")
				);

		return model;
	}
	
	public Pregunta toEntity(PreguntaModel model) {
		Pregunta pregunta = new Pregunta();
		pregunta.setEnunciado(model.getEnunciado());
		return pregunta;
	}
}
