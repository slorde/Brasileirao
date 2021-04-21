package com.fsoft.brasileirao.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fsoft.brasileirao.model.Competicao;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class CompeticaoDetalheDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;	
	private Integer ano;
	private Boolean finalizada;
	private Boolean iniciada;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataCriacao;
		
	private List<ResultadoDTO> resultados = new ArrayList<>();
	private List<ResultadoDonoDTO> participantes = new ArrayList<>();

	
	
	public CompeticaoDetalheDTO(Competicao competicao) {
		this.id = competicao.getId();
		this.ano = competicao.getAno();
		this.dataCriacao = competicao.getDataCriacao();
		this.finalizada = competicao.getFinalizada();
		this.iniciada = competicao.getIniciada();
	}
}
