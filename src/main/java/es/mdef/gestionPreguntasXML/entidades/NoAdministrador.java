package es.mdef.gestionPreguntasXML.entidades;


public class NoAdministrador extends Usuario {

	private static final long serialVersionUID = 1L;
	public static enum Departamento {
        EMIES,
        CCESP
    }

    public static enum Tipo {
        alumno,
        docente,
        administracion
    }

    private Departamento departamento;

    private Tipo tipo;

    public Departamento getDepartamento() {
        return departamento;
    }
    public Tipo getTipo() {
        return tipo;
    }
    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }
    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    @Override
    public Role getRole() {
        return Role.NoAdministrador;
    }
    @Override
    public String toString() {
        return "NoAdministrador [departamento=" + departamento + ", tipo=" + tipo + "]";
    }
}
