package com.fsoft.brasileirao.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Resultado implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "dono_id")
	private Dono dono;
	@OneToMany(mappedBy = "resultado")
	private List<Classificacao> classificacoes = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "competicao_id")
	private Competicao competicao;
	
	private LocalDateTime dataCriacao;
	
	public Resultado(Dono dono, Competicao competicao) {
		this.competicao = competicao;
		this.dataCriacao = LocalDateTime.now();
		this.dono = dono;
	}

	public void addClassificacao(Classificacao classificacao) {
		this.classificacoes.add(classificacao);
	}
}
