package es.mdef.gestionPreguntasXML.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import es.mdef.gestionPreguntasXML.entidades.Usuario;


//  UsuarioRepositorio.java es una interfaz de repositorio que permite interactuar con la base de datos utilizando métodos CRUD heredados 
// de JpaRepository, así como métodos personalizados como findUsuarioByNombreUsuario(). Spring Data JPA se encargará de implementar automáticamente 
// estos métodos siguiendo la convención de nombres de métodos de repositorio.

// JpaRepository: Al extender JpaRepository<Usuario, Long>, esta interfaz hereda automáticamente varios métodos para realizar 
// operaciones CRUD (Crear, Leer, Actualizar y Eliminar) en la entidad Usuario. Los dos parámetros de tipo genérico indican que 
// esta interfaz trabajará con la entidad Usuario y que el tipo de dato de la clave primaria (ID) es Long.

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long>{  
	List<Usuario> findUsuarioByUsername(String username);
	Optional<Usuario> findByUsername(String username);
}