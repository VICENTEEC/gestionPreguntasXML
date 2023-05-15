package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.mdef.gestionPreguntasXML.GestionPreguntasApplication;
import es.mdef.gestionPreguntasXML.entidades.Administrador;
import es.mdef.gestionPreguntasXML.entidades.FamiliaImpl;
import es.mdef.gestionPreguntasXML.entidades.NoAdministrador;
import es.mdef.gestionPreguntasXML.entidades.Pregunta;
import es.mdef.gestionPreguntasXML.entidades.Usuario;
import es.mdef.gestionPreguntasXML.repositorios.UsuarioRepositorio;
import jakarta.persistence.criteria.CriteriaBuilder.Case;
import jakarta.validation.Valid;



	// La clase UsuarioController es la capa de presentación para la API REST de usuario 
	// y se encarga de procesar las solicitudes HTTP, interactuar con el repositorio y 
	// convertir las entidades en modelos de respuesta adecuados para el cliente.
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
	private final UsuarioRepositorio repositorio;
	private final UsuarioAssembler assembler;
	private final UsuarioListaAssembler listaAssembler;
	private final UsuarioPostAssembler postAssembler;
	private final UsuarioPutAssembler putAssembler;
	private final PreguntaListaAssembler preguntaListaAssembler;
	private final FamiliaListaAssembler familiaListaAssembler;
	private final Logger log;
	
	
	// El constructor toma como parámetros un UsuarioRepositorio, un
	// UsuarioAssembler y un UsuarioListaAssembler. Estos componentes son necesarios
	// para interactuar con la base de datos y realizar conversiones entre entidades
	// y modelos. Además, el constructor inicializa un objeto Logger para realizar
	// registros de eventos durante el ciclo de vida del controlador.
	UsuarioController(UsuarioRepositorio repositorio, UsuarioAssembler assembler, UsuarioListaAssembler listaAssembler,
			UsuarioPostAssembler postAssembler, UsuarioPutAssembler putAssembler, PreguntaListaAssembler preguntaListaAssembler, FamiliaListaAssembler familiaListaAssembler) {
		this.repositorio = repositorio;
		this.assembler = assembler;
		this.listaAssembler = listaAssembler;
		this.postAssembler = postAssembler;
		this.putAssembler = putAssembler;
		this.preguntaListaAssembler = preguntaListaAssembler;
		this.familiaListaAssembler = familiaListaAssembler;
		log = GestionPreguntasApplication.log;
	}
	
	// Este método @GetMapping maneja las solicitudes GET para un usuario específico
	// por su ID. Recupera la entidad Usuario del repositorio utilizando el ID
	// proporcionado y convierte la entidad en un modelo EntityModel<Usuario>
	// utilizando el ensamblador. Si el usuario no se encuentra, se lanza una
	// excepción RegisterNotFoundException.
	@GetMapping("{id}")
	public UsuarioModel one(@PathVariable Long id) {
		Usuario usuario = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Recuperado " + usuario);
		return assembler.toModel(usuario);
	}
	
	// Este método @GetMapping maneja las solicitudes GET para todos los usuarios.
	// Recupera todas las entidades Usuario del repositorio y las convierte en una
	// colección de modelos UsuarioListaModel utilizando el ensamblador de lista.
	@GetMapping
	public CollectionModel<UsuarioListaModel> all() {
		log.info("llegaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		return listaAssembler.toCollection(repositorio.findAll());
	}
	
	// Este método @GetMapping maneja las solicitudes GET para buscar usuarios por
	// su nombre de usuario. Utiliza el repositorio para encontrar usuarios que
	// coincidan con el nombre de usuario proporcionado y convierte las entidades
	// encontradas en una colección de modelos UsuarioListaModel utilizando el
	// ensamblador de lista.
	@GetMapping("porNombreUsuario")
	public CollectionModel<UsuarioListaModel> usuariosPorNombreUsuario(@RequestParam String username) {
		return listaAssembler.toCollection(
				repositorio.findUsuarioByUsername(username)
				);
	}
	
	@GetMapping("{id}/preguntas")
	public CollectionModel<PreguntaListaModel> preguntasUsuario(@PathVariable Long id) {
		List<Pregunta> preguntas = repositorio.findById(id)
				.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"))
				.getPreguntas();
		return CollectionModel.of(
				preguntas.stream().map(pregunta -> preguntaListaAssembler.toModel(pregunta)).collect(Collectors.toList()),
				linkTo(methodOn(UsuarioController.class).one(id)).slash("preguntas").withSelfRel()
				);
	}
	
	@GetMapping("{id}/familias")
	public CollectionModel<FamiliaListaModel> familiasUsuario(@PathVariable Long id) {
		   Usuario usuario = repositorio.findById(id)
		            .orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));

		    List<FamiliaImpl> familiasImpl = usuario.getPreguntas().stream()
		            .map(Pregunta::getFamilia)
		            .distinct()
		            .collect(Collectors.toList());

		    return CollectionModel.of(
		    		familiasImpl.stream().map(familiaImpl -> familiaListaAssembler.toModel(familiaImpl)).collect(Collectors.toList()),
		    		linkTo(methodOn(UsuarioController.class).familiasUsuario(id)).withSelfRel()
		    		);
//		    return familiaListaAssembler.toCollection(familiaImpl);	    
	}
	// Este método @PostMapping maneja las solicitudes POST para agregar un nuevo
	// usuario. Convierte el UsuarioModel proporcionado en una entidad Usuario
	// utilizando el ensamblador, luego guarda la entidad en el repositorio y
	// devuelve un modelo EntityModel<Usuario> para el usuario recién creado
	@PostMapping
	public UsuarioModel add(@Valid @RequestBody UsuarioPostModel model) {
		model.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
		Usuario usuario = repositorio.save(postAssembler.toEntity(model));
		log.info("Añadido " + usuario);
		return assembler.toModel(usuario);
	}
	
	
	// Este método @PutMapping maneja las solicitudes PUT para actualizar un usuario
	// existente por su ID. Busca la entidad Usuario en el repositorio, actualiza
	// sus campos utilizando el UsuarioModel proporcionado y guarda la entidad
	// actualizada en el repositorio. Devuelve un modelo EntityModel<Usuario> para
	// el usuario actualizado. Si el usuario no se encuentra, se lanza una excepción
	// RegisterNotFoundException.
	@PutMapping("{id}")
	public UsuarioModel edit(@PathVariable Long id, @RequestBody UsuarioPutModel model) {
		Usuario usuario = repositorio.findById(id).map(usr -> {
			if (usr.getRole() == null) {
			} else {
				switch (usr.getRole()) {
				case Administrador: {
					((Administrador) usr).setTelefono(model.getTelefono());
					break;
				}
				case NoAdministrador: {
					((NoAdministrador) usr).setDepartamento(model.getDepartamento());
					((NoAdministrador) usr).setTipo(model.getTipo());
					break;
				}
				}
			}
			usr.setNombre(model.getNombre());
			usr.setUsername(model.getUsername());
			usr.setAccountNonExpired(model.isAccountNonExpired());
			usr.setAccountNonLocked(model.isAccountNonLocked());
			usr.setCredentialsNonExpired(model.isCredentialsNonExpired());
			usr.setEnabled(model.isEnabled());
			return repositorio.save(usr);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Actualizado " + usuario);
		return assembler.toModel(usuario);
	}
	
	@PatchMapping("{id}/cambiarContrasena")
	public void edit(@PathVariable Long id, @RequestBody String newPassword) {
		Usuario nuevoUsuario = repositorio.findById(id).map(usu -> {
			usu.setPassword(new BCryptPasswordEncoder().encode(newPassword));
			return repositorio.save(usu);
		})
		.orElseThrow(() -> new RegisterNotFoundException(id, "usuario"));
		log.info("Contraseña cambiada al usuario  " + nuevoUsuario);
	}

	// Este método @DeleteMapping maneja las solicitudes DELETE para eliminar un
	// usuario por su ID. Utiliza el repositorio para eliminar la entidad Usuario
	// asociada con el ID proporcionado.
	@DeleteMapping("{id}")
	public void delete(@PathVariable Long id) {
		log.info("Borrado usuario " + id);
		repositorio.deleteById(id);
	}
}