package com.fsoft.brasileirao.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Dono implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@OneToMany(mappedBy = "dono")
	@JsonIgnore
	private List<Resultado> resultados = new ArrayList<>();

	private Boolean isResultado = false;
	
	public Dono(String nome) {
		this.nome = nome;
	}
	
	public void addResultado(Resultado resultado) {
		this.resultados.add(resultado);
	}
}
