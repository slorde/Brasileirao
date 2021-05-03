package com.fsoft.brasileirao.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fsoft.brasileirao.dto.ClassificacaoDTO;
import com.fsoft.brasileirao.dto.InsereResultadoDTO;
import com.fsoft.brasileirao.dto.ResultadoDTO;
import com.fsoft.brasileirao.model.Classificacao;
import com.fsoft.brasileirao.model.Competicao;
import com.fsoft.brasileirao.model.Dono;
import com.fsoft.brasileirao.model.Equipe;
import com.fsoft.brasileirao.model.Resultado;
import com.fsoft.brasileirao.model.enums.Perfil;
import com.fsoft.brasileirao.repository.ClassificacaoRepository;
import com.fsoft.brasileirao.repository.CompeticaoRepository;
import com.fsoft.brasileirao.repository.DonoRepository;
import com.fsoft.brasileirao.repository.ResultadoRepository;
import com.fsoft.brasileirao.service.mapping.ResultadoMapping;

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
	
	@Autowired
	private DonoRepository donoRepository;

	public void updateResultado(Competicao competicao) {
		Resultado resultado = getResultadoAtual(competicao);

		if (resultado != null) {
			if (resultado.getDataCriacao().toLocalDate().isEqual(LocalDateTime.now().toLocalDate()))
				return;
			competicao.removeResultado(resultado);
			classificacaoRespository.deleteAll(resultado.getClassificacoes());
			repository.delete(resultado);
			competicaoRepository.save(competicao);
		}

		criaResultadoAtualizado(competicao);
	}

	@Transactional
	public Resultado getResultadoAtual(Competicao competicao) {
		List<Resultado> resultadosFiltrados = competicao.getResultados().stream()
				.filter(resultado -> resultado.getDono().getNome().equals(DONO_RESULTADO)).collect(Collectors.toList());

		if (resultadosFiltrados.size() > 1)
			throw new RuntimeException("Não devia ter mais de um resultado calculado");

		if (resultadosFiltrados.size() == 1)
			return resultadosFiltrados.get(0);
		
		Dono donoResultado = donoRepository.findByNome(DONO_RESULTADO);
		if (donoResultado == null) {
			donoResultado = new Dono(DONO_RESULTADO);
			donoResultado.setIsResultado(true);
		}
System.out.println("Gera resultado inicial");
		Resultado resultadoAtual = getResultadoInicial(donoResultado, competicao);
		donoResultado.addResultado(resultadoAtual);
		competicao.addResultado(resultadoAtual);
		
		donoRepository.save(donoResultado);
		repository.save(resultadoAtual);
		classificacaoRespository.saveAll(resultadoAtual.getClassificacoes());
		competicaoRepository.save(competicao);

		
		return resultadoAtual;
	}

	@Transactional
	private void criaResultadoAtualizado(Competicao competicao) {
		Dono dono = getDonoResultado();
		Resultado resultado = new Resultado(dono, competicao);
		competicao.addResultado(resultado);

		competicaoRepository.save(competicao);
		repository.save(resultado);

		try {
			String token = System.getenv("API_FUTEBOL_TOKEN");
			HttpRequest request = HttpRequest
					.newBuilder(new URI("https://api.api-futebol.com.br/v1/campeonatos/10/tabela"))
					.header("Authorization", "Bearer " + token).GET().build();

			HttpResponse<String> response = HttpClient.newBuilder().build().send(request, BodyHandlers.ofString());

			ObjectMapper objectMapper = new ObjectMapper();
			List<ResultadoMapping> resultadosEquipes = objectMapper.readValue(response.body(),
					new TypeReference<List<ResultadoMapping>>() {
					});
			for (ResultadoMapping resultadoEquipe : resultadosEquipes) {
				Integer posicao = resultadoEquipe.getPosicao();
				String nomeEquipe = resultadoEquipe.getTime().getNome_popular();
				Equipe equipe = equipeService.findOrCreate(nomeEquipe);

				Classificacao classificacao = new Classificacao(posicao, equipe, resultado);
				resultado.addClassificacao(classificacao);
				classificacaoRespository.save(classificacao);
			}

		} catch (URISyntaxException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
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

	@Transactional
	public void updateResultados() {
		for (Competicao competicao : competicaoService.competicoesAtivas()) {
			System.out.println("Atualizando competição " + competicao.getAno());
			updateResultado(competicao);
		}
	}
	
	public ResultadoDTO buscaResultado(Long competicaoId) {
		Long donoId = UserService.authenticated().getDonoId();
		
		Competicao competicao = competicaoRepository.findById(competicaoId).orElseThrow(() -> new RuntimeException("Competição não encontrada"));
		Dono dono = donoRepository.findById(donoId).orElseThrow(() -> new RuntimeException("Dono não encontrado"));
		
		Resultado resultado = this.repository.findByCompeticaoAndDono(competicao, dono);
		if (resultado == null)
			resultado = getResultadoInicial(dono, competicao);
		
		Resultado resultadoAtual = getResultadoAtual(competicao);

		return create(resultado, resultadoAtual);
	}
	
	public ResultadoDTO create(Resultado resultado, Resultado resultadoAtual) {
		ResultadoDTO resultadoDTO = new ResultadoDTO(resultado);
		
		List<ClassificacaoDTO> classificacoes = resultado.getClassificacoes().stream()
				.sorted(Comparator.comparingInt(Classificacao::getPosicao))
				.map(classificacao -> classificacaoService.create(classificacao, resultadoAtual)).collect(Collectors.toList());

		resultadoDTO.setClassificacoes(classificacoes);

		return resultadoDTO;
	}

	@Transactional
	public void insereResultado(Long competicaoId, InsereResultadoDTO insereResultadoDTO) {
		Competicao competicao = competicaoRepository.findById(competicaoId).orElseThrow(() -> new RuntimeException("Competição não encontrada"));
		
		if (competicao.isIniciada())
			throw new RuntimeException("Competição já iniciada não aceita cadastros");
		
		Resultado resultado = getResultado(insereResultadoDTO, competicao);

		Map<String, Integer> mapaDePontuacao = new HashMap<>();
		for (ClassificacaoDTO classificacaoDTO : insereResultadoDTO.getClassificacoes()) {
			mapaDePontuacao.put(classificacaoDTO.getEquipe(), classificacaoDTO.getPosicao());
		}
		
		for (Classificacao classificacao: resultado.getClassificacoes()) {
			classificacao.setPosicao(mapaDePontuacao.get(classificacao.getEquipe().getNome()));
		}
		

		competicaoRepository.save(competicao);
		repository.save(resultado);
		classificacaoRespository.saveAll(resultado.getClassificacoes());		
	}

	private Resultado getResultado(InsereResultadoDTO insereResultadoDTO, Competicao competicao) {
		Long donoId = UserService.authenticated().getDonoId();

		if (donoId == null && UserService.authenticated().hasRole(Perfil.ADMIN)) {
			donoId = insereResultadoDTO.getDonoId();
		}
		
		Dono dono = donoService.findById(donoId);
		if (dono == null)
			throw new RuntimeException("Dono não encontrado");

		Resultado resultadoDoDono = competicao.getResultados().stream().filter(resultado -> resultado.getDono().equals(dono)).findAny().orElse(null);;
	
		if (resultadoDoDono == null)
			return getResultadoInicial(dono, competicao);
		
		return resultadoDoDono;
	}

	private Resultado getResultadoInicial(Dono dono, Competicao competicao) {
		Resultado resultado = new Resultado(dono, competicao);
		int posicao = 1;
		for (String nomeTime : competicao.getTimes()) {	
			Equipe equipe = equipeService.findOrCreate(nomeTime);
			Classificacao classificacao = new Classificacao(posicao, equipe, resultado);
			resultado.addClassificacao(classificacao);
			posicao++;
		}

		return resultado;
	}

	public List<Resultado> resultadoRaw(Integer ano) {
		Competicao competicao = competicaoRepository.findByAno(ano);
		return competicao.getResultados();
	}
	
	public void delete(Integer ano, Long resultadoId) {
		Competicao competicao = competicaoRepository.findByAno(ano);
		
		Resultado resultado = competicao.getResultados().stream()
				.filter(r -> r.getId().equals(resultadoId))
				.findAny().orElse(null);
		
		competicao.removeResultado(resultado);
		competicaoRepository.save(competicao);
		
		classificacaoRespository.deleteAll(resultado.getClassificacoes());
		repository.delete(resultado);
	}
}
