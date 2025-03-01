package com.portalsocial.portalsocial.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@Table(name = "aula")
@EqualsAndHashCode(onlyExplicitlyIncluded = true,callSuper=false)
public class Aula {
	
	@EqualsAndHashCode.Include
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="aula_id")
	private Long id;
	
 	@ManyToOne 
	@JoinColumn(name ="professor_idprofessor", nullable=false)
 	private Professor professor;
 
 	@ManyToOne 
	@JoinColumn(name ="disciplina_iddisciplina", nullable=false)	
 	private Disciplina disciplina;
	
 	@ManyToOne 
	@JoinColumn(name ="curso_idcurso", nullable=false)	
 	private Curso curso;
 	
 	private String aulaMaterial;
 	
 	private String aulaVideo;
	
}
