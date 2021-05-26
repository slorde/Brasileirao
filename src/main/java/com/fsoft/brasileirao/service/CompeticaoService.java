package com.fsoft.brasileirao.service;

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.dto.BobosDTO;
import com.fsoft.brasileirao.dto.CompeticaoDTO;
import com.fsoft.brasileirao.dto.ResultadoDonoDTO;
import com.fsoft.brasileirao.model.Classificacao;
import com.fsoft.brasileirao.model.Competicao;
import com.fsoft.brasileirao.model.Resultado;
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
				.map(competicao -> competicao.getAno()).sorted(Comparator.reverseOrder())
				.collect(Collectors.toList());
	}
	
	public CompeticaoDTO create(Competicao competicao) {
		CompeticaoDTO competicaoDTO = new CompeticaoDTO(competicao);
		
		competicaoDTO.setParticipantes(getParticipantes(competicao));
		
		return competicaoDTO;
	}

	public List<ResultadoDonoDTO> getParticipantes(Competicao competicao) {
		Resultado resultadoAtual = resultadoService.getResultadoAtual(competicao);
		
		List<ResultadoDonoDTO> participantes = competicao.getResultados().stream()
		.filter(resultado -> !resultado.getDono().getIsResultado())
		.map(resultado -> new ResultadoDonoDTO(resultado.getDono(), resultadoService.create(resultado, resultadoAtual)))
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
		
		resultadoService.getResultadoAtual(competicao);
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

	public String extrat() {
		List<Competicao> competicoes = repository.findAll();
		
		StringBuilder retorno = new StringBuilder();
		for (Competicao competicao : competicoes) {
			retorno.append(competicao.getAno().toString() + "\n");
			
			String donos = "";
			Integer quantiadeCLassificacoes= 20;
			List<Resultado> resultados = competicao.getResultados();
			for(Resultado resultado: resultados) {
				donos += resultado.getDono().getNome() + "\t\t\t";
				quantiadeCLassificacoes = resultado.getClassificacoes().size();
			}
			
			retorno.append(donos);
			retorno.append("\n");
			
			for(int i = 0; i < quantiadeCLassificacoes; i++) {
				String linhaClassificacao = "";
				for (Resultado resultado: resultados) {
					Classificacao classificacao = resultado.getClassificacoes().get(i);
					linhaClassificacao += classificacao.getPosicao() + "\t" + classificacao.getEquipe().getNome() + "\t\t"; 
				}
				retorno.append(linhaClassificacao);
				retorno.append("\n");
			}
			retorno.append("\n");
		}
		
		return retorno.toString();
	}

	public List<BobosDTO> dadosBobos() {
		List<Competicao> competicoes = repository.findAll();
		
		Map<String, Double> campeonatos = new HashMap<>();
		campeonatos.put("farofa", 0d);
		Map<String, Integer> pontuacaoGeral = new HashMap<>();
		List<String> bobos = asList("gugu", "maca", "farofa", "xico");

		for (Competicao competicao : competicoes) {
			List<ResultadoDonoDTO> participantes = getParticipantes(competicao);
			
			List<ResultadoDonoDTO> resultadoBobos = participantes
			.stream()
			.filter(resultadoParticipante -> bobos.contains(resultadoParticipante.getNome()))
			.sorted(Comparator.comparingInt(ResultadoDonoDTO::getPontuacao))
			.collect(Collectors.toList());

			ResultadoDonoDTO campeao = resultadoBobos.get(0);
			List<ResultadoDonoDTO> campeoes = new ArrayList<>();
			for (ResultadoDonoDTO bobo : resultadoBobos) {
				if (bobo.getPontuacao() == campeao.getPontuacao())
					campeoes.add(bobo);
				
				Integer pontuacao = pontuacaoGeral.get(bobo.getNome());
				if (pontuacao == null) {
					pontuacao = 0;
				}
				pontuacaoGeral.put(bobo.getNome(), pontuacao + bobo.getPontuacao());
			}
			
			for (ResultadoDonoDTO ganhador : campeoes) {
				double valorTitulo = Math.round(((double)1 / (double)campeoes.size()) * 100.0) / 100.0;
				
				Double campeonatosDoCampeao = campeonatos.get(ganhador.getNome());
				if (campeonatosDoCampeao == null) {
					campeonatosDoCampeao = 0d;
				}
				campeonatos.put(ganhador.getNome(), (campeonatosDoCampeao + valorTitulo));
			}
		}
		
		return bobos.stream()
				.map(bobo -> new BobosDTO(bobo, pontuacaoGeral.get(bobo), campeonatos.get(bobo)))
				.collect(Collectors.toList());		
	}
}
