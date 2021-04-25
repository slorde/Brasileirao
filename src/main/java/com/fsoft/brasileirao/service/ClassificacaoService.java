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

	@Autowired
	private ResultadoService resultadoService;
	
	public ClassificacaoDTO create(Classificacao classificacao) {
		ClassificacaoDTO classificacaoDTO = new ClassificacaoDTO(classificacao);

		Resultado resultadoAtual = resultadoService.getResultadoAtual(classificacao.getResultado().getCompeticao());
		System.out.println("classificacao");
		System.out.println(classificacao.getEquipe().getNome());
		System.out.println("Resultado");
		for (Classificacao cl : resultadoAtual.getClassificacoes()) {
			System.out.println(cl.getEquipe().getNome());
		}
		
		
		List<Classificacao> classificacaoAtualDaEquipe = resultadoAtual.getClassificacoes().stream().filter(classificacaoAtual -> classificacao.getEquipe().equals(classificacaoAtual.getEquipe())).collect(Collectors.toList());
		
		classificacaoDTO.setPontuacao(classificacao.getPosicao() - classificacaoAtualDaEquipe.get(0).getPosicao());
		
		return classificacaoDTO;
	}
}
