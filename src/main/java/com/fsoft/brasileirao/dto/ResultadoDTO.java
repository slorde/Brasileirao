package com.fsoft.brasileirao.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fsoft.brasileirao.model.Dono;
import com.fsoft.brasileirao.model.Resultado;

import lombok.Getter;

@Getter
public class ResultadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Dono dono;
	private List<ClassificacaoDTO> classificacoes = new ArrayList<>();

	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataCriacao;

	private Integer pontuacao;

	public ResultadoDTO(Resultado resultado) {
		this.dono = resultado.getDono();
		this.dataCriacao = resultado.getDataCriacao();
	}

	public void setClassificacoes(List<ClassificacaoDTO> classificacoes) {
		this.classificacoes = classificacoes;
		this.pontuacao = classificacoes.stream()
				  .mapToInt(classificacao -> Math.abs(classificacao.getPontuacao()))
				  .sum();
	}
}
