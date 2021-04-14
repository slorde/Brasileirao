package com.fsoft.brasileirao.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.brasileirao.dto.CompeticaoDTO;
import com.fsoft.brasileirao.dto.CriaCompeticaoDTO;
import com.fsoft.brasileirao.dto.ResultadoDTO;
import com.fsoft.brasileirao.service.CompeticaoService;
import com.fsoft.brasileirao.service.ResultadoService;

@CrossOrigin
@RestController
@RequestMapping("competicoes")
public class CompeticaoController {

	@Autowired
	private CompeticaoService service;
	
	@Autowired
	private ResultadoService resultadoService;

	@GetMapping(value = "/anos")
	public ResponseEntity<List<Integer>> anos() {
		return ResponseEntity.ok(service.anosComCompeticao());
	}

	@GetMapping(value = "/ativas")
	public ResponseEntity<List<CompeticaoDTO>> competicoesAtivas() {
		List<CompeticaoDTO> competicoesAtivasDTO = service.competicoesAtivas().stream()
				.map(competicao -> service.create(competicao)).collect(Collectors.toList());
		return ResponseEntity.ok(competicoesAtivasDTO);
	}

	@GetMapping(value = "/{ano}/resultados")
	public ResponseEntity<List<ResultadoDTO>> resultados(@PathVariable Integer ano) {
		List<ResultadoDTO> resultadosDTO = service.resultados(ano).stream().map(resultado -> resultadoService.create(resultado))
				.collect(Collectors.toList());
		return ResponseEntity.ok(resultadosDTO);
	}
	
	@PostMapping("/")
	public ResponseEntity<Void> criaCompeticao(@RequestBody CriaCompeticaoDTO criaCompeticaoDTO) {
		service.criaCompeticao(criaCompeticaoDTO.getAno(), criaCompeticaoDTO.getTimes());
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/finalizar")
	public ResponseEntity<Void> finalizaCompeticao(@PathVariable Long id) {
		service.finalizaCompeticao(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping("/{id}/iniciar")
	public ResponseEntity<Void> iniciaCompeticao(@PathVariable Long id) {
		service.iniciaCompeticao(id);
		return ResponseEntity.noContent().build();
	}
}
