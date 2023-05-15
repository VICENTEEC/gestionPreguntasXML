package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionPreguntasXML.entidades.FamiliaImpl;


@Component
public class FamiliaAssembler implements RepresentationModelAssembler<FamiliaImpl, FamiliaModel>{

	public FamiliaModel toModel(FamiliaImpl entity) {
		FamiliaModel model = new FamiliaModel();
		
		model.setEnunciado(entity.getEnunciado());
		model.setTamano(entity.getTamano());
		model.add(
				linkTo(methodOn(FamiliaController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(FamiliaController.class).usuariosEnFamilias(entity.getId())).withRel("usuariosDesdeFamiliaAssembler"),
				linkTo(methodOn(FamiliaController.class).preguntasEnFamilias(entity.getId())).withRel("preguntasDesdeFamiliaAssembler")
				);
		return model;		
	}
	
	public FamiliaImpl toEntity(FamiliaModel model) {
		FamiliaImpl familiaImpl = new FamiliaImpl();
		familiaImpl.setEnunciado(model.getEnunciado());
		return familiaImpl;
	}
}
