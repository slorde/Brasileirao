package com.fsoft.brasileirao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
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
public class Competicao implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	private Integer ano;
	private Boolean finalizada;
	private LocalDateTime dataCriacao;
	
	@OneToMany(mappedBy = "competicao")
	@JsonIgnore
	private List<Resultado> resultados = new ArrayList<>();
	
	public Competicao(Integer ano, Boolean finalizada) {
		this.finalizada = finalizada;
		this.dataCriacao = LocalDateTime.now();
		this.ano = ano;
	}

	public void addResultado(Resultado resultado) {
		this.resultados.add(resultado);
	}

	public void removeResultado(Resultado resultado) {
		this.resultados.remove(resultado);
	}
}
