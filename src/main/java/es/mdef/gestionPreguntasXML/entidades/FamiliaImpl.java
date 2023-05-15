package es.mdef.gestionPreguntasXML.entidades;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="familias")
public class FamiliaImpl extends es.mdef.support.Familia{
	@Column(unique=true, name = "id_familia")
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

	@OneToMany(mappedBy = "familia")                                          //RELACIONES
	private List<Pregunta> preguntas = new ArrayList<>();
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public long getTamano() {
		return getPreguntas().size();
	}
	public List<Pregunta> getPreguntas() {
		return preguntas;
	}
	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	@Override
	public String toString() {
		return "FamiliaImpl [id=" + id + ", preguntas=" + preguntas + "]";
	}
	
}
