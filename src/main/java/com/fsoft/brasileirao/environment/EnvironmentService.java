package com.fsoft.brasileirao.environment;

import static com.fsoft.brasileirao.model.enums.Perfil.ADMIN;
import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.model.Classificacao;
import com.fsoft.brasileirao.model.Competicao;
import com.fsoft.brasileirao.model.Dono;
import com.fsoft.brasileirao.model.Equipe;
import com.fsoft.brasileirao.model.Resultado;
import com.fsoft.brasileirao.model.Usuario;
import com.fsoft.brasileirao.repository.ClassificacaoRepository;
import com.fsoft.brasileirao.repository.CompeticaoRepository;
import com.fsoft.brasileirao.repository.DonoRepository;
import com.fsoft.brasileirao.repository.EquipeRepository;
import com.fsoft.brasileirao.repository.ResultadoRepository;
import com.fsoft.brasileirao.repository.UsuarioRepository;

@Service
@Transactional
public class EnvironmentService {

	@Autowired
	private EquipeRepository equipeRepository;

	@Autowired
	private DonoRepository donoRepository;

	@Autowired
	private ResultadoRepository resultadoRepository;

	@Autowired
	private ClassificacaoRepository classificacaoRepository;

	@Autowired
	private CompeticaoRepository competicaoRepository;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public void execute() {

		List<String> nomesEquipes = Arrays.asList("Atlético-MG", "Palmeiras", "Flamengo", "Grêmio", "Internacional",
				"São Paulo", "Athletico-PR", "Santos", "Corinthians", "Bahia", "Fluminense", "Ceará", "Botafogo",
				"Fortaleza", "Vasco", "Coritiba", "Bragantino", "Goiás", "Sport", "Atlético-GO");

		Map<String, Equipe> equipes = new HashMap<>();
		for (String nomeEquipe : nomesEquipes) {
			equipes.put(nomeEquipe, new Equipe(nomeEquipe));
		}
		equipeRepository.saveAll(equipes.values());

		Competicao competicao = new Competicao(2020, false);

		Dono donoXico = new Dono("Xico");
		Dono donoMaca = new Dono("Maca");
		Dono donoGugu = new Dono("Gugu");
		Dono donoFarofa = new Dono("Farofa");
		Dono donoGlobo = new Dono("Globo");
		Dono donoEspn = new Dono("Espn");

		competicaoRepository.save(competicao);
		donoRepository.saveAll(asList(donoXico));

		criaResultado(donoXico, competicao, equipes, asList("1", "Atlético-MG"), asList("2", "Palmeiras"),
				asList("3", "Flamengo"), asList("4", "Grêmio"), asList("5", "Internacional"), asList("6", "São Paulo"),
				asList("7", "Athletico-PR"), asList("8", "Santos"), asList("9", "Corinthians"), asList("10", "Bahia"),
				asList("11", "Fluminense"), asList("12", "Ceará"), asList("13", "Botafogo"), asList("14", "Fortaleza"),
				asList("15", "Vasco"), asList("16", "Coritiba"), asList("17", "Bragantino"), asList("18", "Goiás"),
				asList("19", "Sport"), asList("20", "Atlético-GO"));

		criaResultado(donoMaca, competicao, equipes, asList("1", "Atlético-MG"), asList("2", "Palmeiras"),
				asList("3", "Flamengo"), asList("4", "Corinthians"), asList("5", "Internacional"),
				asList("6", "São Paulo"), asList("7", "Athletico-PR"), asList("8", "Bragantino"), asList("9", "Grêmio"),
				asList("10", "Santos"), asList("11", "Fortaleza"), asList("12", "Fluminense"), asList("13", "Coritiba"),
				asList("14", "Vasco"), asList("15", "Ceará"), asList("16", "Bahia"), asList("17", "Goiás"),
				asList("18", "Botafogo"), asList("19", "Atlético-GO"), asList("20", "Sport"));

		criaResultado(donoGugu, competicao, equipes, asList("1", "Flamengo"), asList("2", "Palmeiras"),
				asList("3", "Internacional"), asList("4", "Grêmio"), asList("5", "São Paulo"),
				asList("6", "Athletico-PR"), asList("7", "Atlético-MG"), asList("8", "Corinthians"),
				asList("9", "Fluminense"), asList("10", "Santos"), asList("11", "Bahia"), asList("12", "Bragantino"),
				asList("13", "Fortaleza"), asList("14", "Botafogo"), asList("15", "Goiás"), asList("16", "Coritiba"),
				asList("17", "Vasco"), asList("18", "Sport"), asList("19", "Ceará"), asList("20", "Atlético-GO"));

		criaResultado(donoFarofa, competicao, equipes, asList("1", "Palmeiras"), asList("2", "Flamengo"),
				asList("3", "Grêmio"), asList("4", "Atlético-MG"), asList("5", "São Paulo"),
				asList("6", "Internacional"), asList("7", "Corinthians"), asList("8", "Santos"),
				asList("9", "Athletico-PR"), asList("10", "Vasco"), asList("11", "Fluminense"),
				asList("12", "Botafogo"), asList("13", "Fortaleza"), asList("14", "Coritiba"), asList("15", "Sport"),
				asList("16", "Bragantino"), asList("17", "Bahia"), asList("18", "Goiás"), asList("19", "Ceará"),
				asList("20", "Atlético-GO"));

		criaResultado(donoGlobo, competicao, equipes, asList("1", "Flamengo"), asList("2", "Palmeiras"),
				asList("3", "Grêmio"), asList("4", "Atlético-MG"), asList("5", "Corinthians"), asList("6", "São Paulo"),
				asList("7", "Athletico-PR"), asList("8", "Internacional"), asList("9", "Bahia"),
				asList("10", "Bragantino"), asList("11", "Fluminense"), asList("12", "Santos"), asList("13", "Ceará"),
				asList("14", "Fortaleza"), asList("15", "Botafogo"), asList("16", "Vasco"), asList("17", "Atlético-GO"),
				asList("18", "Goiás"), asList("19", "Coritiba"), asList("20", "Sport"));

		criaResultado(donoEspn, competicao, equipes, asList("1", "Flamengo"), asList("2", "Atlético-MG"),
				asList("3", "Palmeiras"), asList("4", "Internacional"), asList("5", "Grêmio"),
				asList("6", "Corinthians"), asList("7", "São Paulo"), asList("8", "Athletico-PR"),
				asList("9", "Bragantino"), asList("10", "Bahia"), asList("11", "Fortaleza"), asList("12", "Fluminense"),
				asList("13", "Santos"), asList("14", "Ceará"), asList("15", "Vasco"), asList("16", "Botafogo"),
				asList("17", "Coritiba"), asList("18", "Goiás"), asList("19", "Sport"), asList("20", "Atlético-GO"));

		criaUsuario("Xico", "teste123", true);
		criaUsuario("Maca", "maca", false);

	}

	@SafeVarargs
	private void criaResultado(Dono dono, Competicao competicao, Map<String, Equipe> equipes,
			List<String>... resultados) {
		Resultado resultado = new Resultado(dono, competicao);
		dono.addResultado(resultado);

		List<Classificacao> classificacoes = new ArrayList<>();
		for (List<String> resultadosInformados : resultados) {
			Classificacao classificacao = new Classificacao(Integer.valueOf(resultadosInformados.get(0)),
					equipes.get(resultadosInformados.get(1)), resultado);
			resultado.addClassificacao(classificacao);
			classificacoes.add(classificacao);
		}

		competicao.addResultado(resultado);
		resultado.setCompeticao(competicao);

		competicaoRepository.save(competicao);
		donoRepository.save(dono);
		resultadoRepository.save(resultado);
		classificacaoRepository.saveAll(classificacoes);
	}
	
	public void criaUsuario(String login, String senha, boolean admin) {
		Usuario usuario = new Usuario(login, passwordEncoder.encode(senha));
		if (admin) usuario.addPerfi(ADMIN);
		usuarioRepository.save(usuario);
	
		Dono dono = donoRepository.findByNome(login);
		dono.setUsuario(usuario);
		donoRepository.save(dono);
		
		usuario.addDono(dono);
		usuarioRepository.save(usuario); 
	}
}
