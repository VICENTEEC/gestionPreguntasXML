package es.mdef.gestionPreguntasXML.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import es.mdef.gestionPreguntasXML.entidades.NoAdministrador.Departamento;
import es.mdef.gestionPreguntasXML.entidades.NoAdministrador.Tipo;
import es.mdef.gestionPreguntasXML.entidades.Usuario.Role;
import jakarta.persistence.Column;

@Relation(itemRelation="usuario")
public class UsuarioModel extends RepresentationModel<UsuarioModel>{
	
	private String nombre;
	private String username;
	private Departamento departamento;
	private Tipo tipo;
	private String telefono;
	private Role role;
	private boolean accountNonExpired;
	private boolean accountNonLocked;
	private boolean credentialsNonExpired;
	private boolean enabled;
	
	public String getNombre() {
		return nombre;
	}
	
	public String getUsername() {
		return username;
	}
	
	public Departamento getDepartamento() {
		return departamento;
	}
	
	public Tipo getTipo() {
		return tipo;
	}

	public String getTelefono() {
		return telefono;
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
	
	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
	
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}