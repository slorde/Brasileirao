package com.fsoft.brasileirao.service.mapping;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultadoMapping implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer posicao;
	private Integer pontos;
	private TimeMapping time;
	private Integer jogos;
	private Integer vitorias;
	private Integer empates;
	private Integer derrotas;
	private Integer gols_pro;
	private Integer gols_contra;
	private Integer saldo_gols;
	private Double aproveitamento;
	private Integer variacao_posicao;
	private String[] ultimos_jogos;
}
