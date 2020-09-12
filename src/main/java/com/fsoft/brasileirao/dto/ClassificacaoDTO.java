package com.fsoft.brasileirao.dto;

import java.io.Serializable;

import com.fsoft.brasileirao.model.Classificacao;

import lombok.Getter;

@Getter
public class ClassificacaoDTO  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer posicao;
	private String equipe;
	private Integer pontuacao;
	
	public ClassificacaoDTO(Classificacao classificacao) {
		this.posicao = classificacao.getPosicao();
		this.equipe = classificacao.getEquipe().getNome();
	}
	
	public void setPontuacao(Integer pontuacao) {
		this.pontuacao = pontuacao;
	}
}
