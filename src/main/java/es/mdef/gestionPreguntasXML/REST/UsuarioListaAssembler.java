package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionPreguntasXML.entidades.Usuario;


// La clase UsuarioListaAssembler.java es otro componente que se utiliza para convertir entidades Usuario 
// en modelos de representación de la API REST, específicamente en UsuarioListaModel, y viceversa.

// La clase UsuarioListaAssembler es similar a la clase UsuarioAssembler, pero se centra en trabajar con listas 
// de usuarios en lugar de usuarios individuales. Esta clase facilita la conversión entre entidades de dominio y 
// modelos de representación utilizados por la API REST, especialmente para colecciones de recursos.
@Component
public class UsuarioListaAssembler implements RepresentationModelAssembler<Usuario, UsuarioListaModel>{

	
	// Este método toma una entidad Usuario como argumento y crea un
	// UsuarioListaModel a partir de ella. El UsuarioListaModel se crea como una
	// nueva instancia, y se copian los campos nombre, nombreUsuario y role de la
	// entidad al modelo. Además, se agrega un enlace HATEOAS al modelo usando los
	// métodos add y linkTo. El enlace HATEOAS apunta al método one del controlador
	// UsuarioController, permitiendo a los clientes de la API navegar fácilmente a
	// los detalles de un usuario específico.
	
	@Override
	public UsuarioListaModel toModel(Usuario entity) {
		UsuarioListaModel model = new UsuarioListaModel();
		model.setNombre(entity.getNombre());
		model.setUsername(entity.getUsername());
		model.setRole(entity.getRole());
		model.add(
				linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel()
				);
		return model;
	}
	
	
	// Este método toma una lista de entidades Usuario y convierte cada entidad en
	// un UsuarioListaModel utilizando el método toModel. Luego, crea un
	// CollectionModel<UsuarioListaModel> utilizando el método estático
	// CollectionModel.of y agrega un enlace HATEOAS a la colección con el método
	// add. El enlace HATEOAS apunta al método all del controlador
	// UsuarioController, proporcionando una forma fácil para que los clientes de la
	// API naveguen a la lista completa de usuarios.
	public CollectionModel<UsuarioListaModel> toCollection(List<Usuario> lista) {
		CollectionModel<UsuarioListaModel> collection = CollectionModel.of(
				lista.stream().map(this::toModel).collect(Collectors.toList())
				);
		collection.add(
				linkTo(methodOn(UsuarioController.class).all()).withRel("usuarios")
				);
		return collection;
	}
}
