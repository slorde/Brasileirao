package com.fsoft.brasileirao.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class InsereResultadoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ClassificacaoDTO> classificacoes = new ArrayList<>();
}
