package es.mdef.gestionPreguntasXML.entidades;


public class Administrador extends Usuario{

	private static final long serialVersionUID = 1L;
	private String telefono;

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
	public Role getRole() {
		return Role.Administrador;
	}

	@Override
	public String toString() {
		return "Administrador [telefono=" + telefono + "]";
	}
	
}
