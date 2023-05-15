package es.mdef.gestionPreguntasXML.REST;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import es.mdef.gestionPreguntasXML.entidades.Administrador;
import es.mdef.gestionPreguntasXML.entidades.NoAdministrador;
import es.mdef.gestionPreguntasXML.entidades.Usuario;

@Component
public class UsuarioPutAssembler implements RepresentationModelAssembler<Usuario, UsuarioPutModel> {

    @Override
    public UsuarioPutModel toModel(Usuario entity) {
        UsuarioPutModel model = new UsuarioPutModel();

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
        model.add(linkTo(methodOn(UsuarioController.class).one(entity.getId())).withSelfRel());
        return model;
    }

    public Usuario toEntity(UsuarioPutModel model) {
        Usuario usuario;

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
				throw new IllegalStateException("Invalid role: " + model.getRole());
			}

        usuario.setNombre(model.getNombre());
        usuario.setUsername(model.getUsername());
        return usuario;
    }

}
