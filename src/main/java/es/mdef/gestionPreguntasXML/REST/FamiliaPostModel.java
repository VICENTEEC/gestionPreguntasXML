package es.mdef.gestionPreguntasXML.REST;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import jakarta.validation.constraints.NotBlank;

@Relation(itemRelation="familia")
public class FamiliaPostModel extends RepresentationModel <FamiliaPostModel>{
	
	@NotBlank(message="enunciado es obligatorio en la clase FamiliaPostModel")
	private String enunciado;

	public String getEnunciado() {
		return enunciado;
	}

	public void setEnunciado(String enunciado) {
		this.enunciado = enunciado;
	}

	@Override
	public String toString() {
		return "FamiliaPostModel [enunciado=" + enunciado + "]";
	}
}
