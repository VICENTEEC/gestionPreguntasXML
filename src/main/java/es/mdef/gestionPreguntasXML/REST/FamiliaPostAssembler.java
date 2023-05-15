package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import es.mdef.gestionPreguntasXML.entidades.FamiliaImpl;
import es.mdef.gestionPreguntasXML.entidades.Pregunta;


@Component
public class FamiliaPostAssembler implements RepresentationModelAssembler<FamiliaImpl, FamiliaPostModel>{
	
	public FamiliaPostModel toModel(FamiliaImpl entity) {
		FamiliaPostModel model = new FamiliaPostModel();
		WebMvcLinkBuilder selfLink = linkTo(methodOn(FamiliaController.class).one(entity.getId()));
		model.add(
			selfLink.withSelfRel(),
			selfLink.slash("preguntas").withRel("preguntas")
		);
		return model;
	}

	
	public FamiliaImpl toEntity(FamiliaPostModel model) {
		FamiliaImpl familiaImpl = new FamiliaImpl();
		familiaImpl.setEnunciado(model.getEnunciado());
		
		return familiaImpl;
	}

}
