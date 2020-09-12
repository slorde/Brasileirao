package com.fsoft.brasileirao.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.brasileirao.dto.ClassificacaoDTO;
import com.fsoft.brasileirao.dto.ResultadoDTO;
import com.fsoft.brasileirao.model.Classificacao;
import com.fsoft.brasileirao.model.Competicao;
import com.fsoft.brasileirao.model.Dono;
import com.fsoft.brasileirao.model.Equipe;
import com.fsoft.brasileirao.model.Resultado;
import com.fsoft.brasileirao.repository.ClassificacaoRepository;
import com.fsoft.brasileirao.repository.CompeticaoRepository;
import com.fsoft.brasileirao.repository.ResultadoRepository;

@Service
public class ResultadoService {

	private static final String DONO_RESULTADO = "RESULTADO";

	@Autowired
	private ResultadoRepository repository;

	@Autowired
	private DonoService donoService;
	
	@Autowired
	private EquipeService equipeService;
	
	@Autowired
	private ClassificacaoRepository classificacaoRespository;
	
	@Autowired
	private CompeticaoService competicaoService;
	
	@Autowired
	private ClassificacaoService classificacaoService;
	
	@Autowired
	private CompeticaoRepository competicaoRepository;

	public void updateResultado(Competicao competicao) {
		Resultado resultado = getResultadoAtual(competicao);
		
		if (resultado != null) {
			if (resultado.getDataCriacao().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
				return;
			
			competicao.removeResultado(resultado);
		}
				
		criaResultadoAtualizado(competicao);
	}

	public Resultado getResultadoAtual(Competicao competicao) {
		List<Resultado> resultadosFiltrados = competicao.getResultados().stream().filter(
				resultado -> resultado.getDono().getNome().equals(DONO_RESULTADO))
				.collect(Collectors.toList());
		
		if (resultadosFiltrados.size() > 1)
			throw new RuntimeException("Não devia ter mais de um resultado calculado");
		
		if (resultadosFiltrados.size() == 1)
			return resultadosFiltrados.get(0);
		
		return null;
	}

	@Transactional
	private void criaResultadoAtualizado(Competicao competicao) {
		Dono dono = getDonoResultado();
		Resultado resultado = new Resultado(dono, competicao);
		competicao.addResultado(resultado);
		
		competicaoRepository.save(competicao);
		repository.save(resultado);
		
		try {
			HttpRequest request = HttpRequest.newBuilder(new URI("https://api.api-futebol.com.br/v1/campeonatos/10/tabela"))
			.header("Authorization", "Bearer live_9db8770850035681cf97b5c5f39ecd")
			.GET()
			.build();
			
			HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());
			
			ObjectMapper objectMapper = new ObjectMapper();
			List<Map> resultadosEquipes = objectMapper.readValue(response.body(), List.class);
			for(Map resultadoEquipe: resultadosEquipes) {
				Integer posicao = (Integer) resultadoEquipe.get("posicao");
				String nomeEquipe = (String) ((Map)resultadoEquipe.get("time")).get("nome_popular");
				Equipe equipe = equipeService.findOrCreate(nomeEquipe);
				
				Classificacao classificacao = new Classificacao(posicao, equipe, resultado);
				resultado.addClassificacao(classificacao);
				classificacaoRespository.save(classificacao);
			}
			
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	public Dono getDonoResultado() {
		Dono dono = donoService.findResultado();

		if (dono == null) {
			dono = donoService.create(DONO_RESULTADO);
			dono.setIsResultado(true);
		}

		return dono;
	}

	public void updateResultados() {
		for (Competicao competicao : competicaoService.competicoesAtivas()) {
			updateResultado(competicao);
		}
	}
	
	public ResultadoDTO create(Resultado resultado) {
		ResultadoDTO resultadoDTO = new ResultadoDTO(resultado);
		
		List<ClassificacaoDTO> classificacoes = resultado.getClassificacoes().stream()
		.map(classificacao -> classificacaoService.create(classificacao)).collect(Collectors.toList());

		resultadoDTO.setClassificacoes(classificacoes);
		
		return resultadoDTO;
	}
}
