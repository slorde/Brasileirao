package com.fsoft.brasileirao.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.dto.ClassificacaoDTO;
import com.fsoft.brasileirao.model.Classificacao;
import com.fsoft.brasileirao.model.Resultado;

@Service
public class ClassificacaoService {
	
	public ClassificacaoDTO create(Classificacao classificacao, Resultado  resultadoAtual) {
		ClassificacaoDTO classificacaoDTO = new ClassificacaoDTO(classificacao);
		
		List<Classificacao> classificacaoAtualDaEquipe = resultadoAtual.getClassificacoes().stream().filter(classificacaoAtual -> classificacao.getEquipe().equals(classificacaoAtual.getEquipe())).collect(Collectors.toList());
		
		classificacaoDTO.setPontuacao(classificacao.getPosicao() - classificacaoAtualDaEquipe.get(0).getPosicao());
		
		return classificacaoDTO;
	}
}
