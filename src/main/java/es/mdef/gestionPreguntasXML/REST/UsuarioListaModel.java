package es.mdef.gestionPreguntasXML.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.gestionPreguntasXML.entidades.Usuario.Role;

@Relation(collectionRelation = "usuarios")
public class UsuarioListaModel extends RepresentationModel<UsuarioListaModel>{
	private String nombre;
	private String username;
	private Role role;
	
	public String getNombre() {
		return nombre;
	}
	
	public String getUsername() {
		return username;
	}

	public Role getRole() {
		return role;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "UsuarioListaModel [nombre=" + nombre + ", role=" + role + "]";
	}
	
}