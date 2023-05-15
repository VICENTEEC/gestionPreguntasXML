package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionPreguntasXML.entidades.Administrador;
import es.mdef.gestionPreguntasXML.entidades.NoAdministrador;
import es.mdef.gestionPreguntasXML.entidades.Usuario;

// La clase UsuarioAssembler.java es un componente que se utiliza para convertir entidades Usuario en modelos de 
// representación de la API REST (en este caso, EntityModel<Usuario>), y viceversa. Esta clase implementa la interfaz 
// RepresentationModelAssembler<Usuario, EntityModel<Usuario>> de Spring HATEOAS y anota con @Component para que Spring 
// la reconozca como un bean y la gestione automáticamente. 
@Component
public class UsuarioAssembler implements RepresentationModelAssembler<Usuario, UsuarioModel>{
	
	// Este método toma una entidad Usuario como argumento y crea un
	// EntityModel<Usuario> a partir de ella. El EntityModel se crea con el método
	// estático EntityModel.of(entity) y se le agrega un enlace HATEOAS con el
	// método add. El enlace HATEOAS apunta al método one del controlador
	// UsuarioController, permitiendo a los clientes de la API navegar fácilmente a
	// los detalles de un usuario específico.
	
	@Override
	public UsuarioModel toModel(Usuario entity) {
		entity.setPassword(null); ///////////////////////////////////
		UsuarioModel model = new UsuarioModel();
		
		switch (entity.getRole()) {
			case Administrador: {
				Administrador adm = (Administrador) entity;
				model.setTelefono(adm.getTelefono());
				break;
			}
			case NoAdministrador: {
				NoAdministrador noAdministrador = (NoAdministrador) entity;
				model.setDepartamento(noAdministrador.getDepartamento());
				model.setTipo(noAdministrador.getTipo());
			}
			default:
		}
        model.setNombre(entity.getNombre());
        model.setUsername(entity.getUsername());
        model.setRole(entity.getRole());
        model.setAccountNonExpired(entity.isAccountNonExpired());
        model.setAccountNonLocked(entity.isAccountNonLocked());
        model.setCredentialsNonExpired(entity.isCredentialsNonExpired());
        model.setEnabled(entity.isEnabled());
		model.add(
				linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel(),
				linkTo(methodOn(UsuarioController.class).preguntasUsuario(entity.getId())).withRel("preguntasdesdeUsuarioAssembler"),
				linkTo(methodOn(UsuarioController.class).familiasUsuario(entity.getId())).withRel("familiasdesdeUsuarioAssembler")
				);
		return model;
	}
	
	// Este método hace lo contrario al método toModel. Toma un UsuarioModel como
	// argumento y crea una nueva instancia de la entidad Usuario a partir de él.
	// Copia los campos nombre, nombreUsuario y role del modelo al objeto de entidad
	// creado.
	public Usuario toEntity(UsuarioModel model) {
		Usuario usuario = new Usuario();
		
		switch (model.getRole()) {
			case Administrador: {
				Administrador adm = new Administrador();
				adm.setTelefono(model.getTelefono());
				usuario = adm;
				break;
			}
			case NoAdministrador: {
				NoAdministrador noAdministrador = new NoAdministrador();
				noAdministrador.setDepartamento(model.getDepartamento());
				noAdministrador.setTipo(model.getTipo());
				usuario = noAdministrador;
				break;
			}
			default:
				throw new IllegalArgumentException("Unexpected value: " + model.getRole());
			}
		usuario.setNombre(model.getNombre());
		usuario.setUsername(model.getUsername());
		return usuario;
	}
}