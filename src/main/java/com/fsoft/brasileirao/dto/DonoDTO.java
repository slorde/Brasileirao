package com.fsoft.brasileirao.dto;

import com.fsoft.brasileirao.model.Dono;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class DonoDTO {

	private Long id;
	private String nome;
	private Integer pontuacao;
	
	public DonoDTO(Dono dono, ResultadoDTO resultadoDTO) {
		this.id = dono.getId();
		this.nome = dono.getNome();
		this.pontuacao = resultadoDTO.getPontuacao();
	}
	
}
