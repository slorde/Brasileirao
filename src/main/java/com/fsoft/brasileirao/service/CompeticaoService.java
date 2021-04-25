package com.fsoft.brasileirao.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.dto.CompeticaoDTO;
import com.fsoft.brasileirao.dto.ResultadoDonoDTO;
import com.fsoft.brasileirao.model.Competicao;
import com.fsoft.brasileirao.repository.CompeticaoRepository;

@Service
public class CompeticaoService {

	@Autowired
	private CompeticaoRepository repository;
	
	@Autowired
	private ResultadoService resultadoService;

	public List<Competicao> competicoesAtivas() {
		return repository.findByFinalizadaFalse();
	}

	public Competicao competicao(Integer ano) {
		Competicao competicao = repository.findByAno(ano);
		
		if (competicao.isIniciada())
			return competicao;
		else
			throw new RuntimeException("Competição não iniciada");
	}

	public List<Integer> anosComCompeticaoFinalizada() {
		List<Competicao> competicoes = repository.findAll();
		return competicoes.stream()
				.filter(competicao -> competicao.isFinalizada())
				.map(competicao -> competicao.getAno()).sorted()
				.collect(Collectors.toList());
	}
	
	public CompeticaoDTO create(Competicao competicao) {
		CompeticaoDTO competicaoDTO = new CompeticaoDTO(competicao);
		
		competicaoDTO.setParticipantes(getParticipantes(competicao));
		
		return competicaoDTO;
	}

	public List<ResultadoDonoDTO> getParticipantes(Competicao competicao) {
		List<ResultadoDonoDTO> participantes = competicao.getResultados().stream()
		.filter(resultado -> !resultado.getDono().getIsResultado())
		.map(resultado -> new ResultadoDonoDTO(resultado.getDono(), resultadoService.create(resultado)))
		.sorted(Comparator.comparingInt(ResultadoDonoDTO::getPontuacao))
		.collect(Collectors.toList());
		return participantes;
	}

	public void criaCompeticao(Integer ano, List<String> times) {
		Competicao competicao = repository.findByAno(ano);
		if (competicao == null)
			competicao = new Competicao(ano, false);
		
		competicao.setTimes(times);
		repository.save(competicao);
	}
	
	public void finalizaCompeticao(Long id) {
		Competicao competicao = repository.findById(id).orElse(null);
		if (competicao == null)
			return;
		
		competicao.setFinalizada(true);
		repository.save(competicao);
	}
	
	public void iniciaCompeticao(Long id) {
		Competicao competicao = repository.findById(id).orElse(null);
		if (competicao == null)
			return;
		
		competicao.setIniciada(true);
		repository.save(competicao);
	}
}
