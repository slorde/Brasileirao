package com.fsoft.brasileirao.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BobosDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nome;	
	private Integer pontuacao;
	private Double qtdCampeao;
}
