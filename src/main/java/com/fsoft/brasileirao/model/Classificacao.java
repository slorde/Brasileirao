package com.fsoft.brasileirao.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Classificacao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer posicao;
	@ManyToOne
	@JoinColumn(name = "equipe_id")
	private Equipe equipe;
	@ManyToOne
	@JoinColumn(name = "resultado_id")
	@JsonIgnore
	private Resultado resultado;
	
	public Classificacao(Integer posicao, Equipe equipe, Resultado resultado) {
		this.posicao = posicao;
		this.equipe = equipe;
		this.resultado = resultado;
	}
}
