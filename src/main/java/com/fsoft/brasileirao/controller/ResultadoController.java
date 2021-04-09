package com.fsoft.brasileirao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.brasileirao.dto.InsereResultadoDTO;
import com.fsoft.brasileirao.dto.ResultadoDTO;
import com.fsoft.brasileirao.service.ResultadoService;

@CrossOrigin
@RestController
@RequestMapping("resultados")
public class ResultadoController {

	@Autowired
	private ResultadoService service;
	
	@PostMapping("/update")
	public ResponseEntity<Void> updateResultados() {
		service.updateResultados();
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/competicao/{competicaoId}")
	public ResponseEntity<Void> insereResultado(@PathVariable Long competicaoId, @RequestBody InsereResultadoDTO insereResultadoDTO) {
		service.insereResultado(competicaoId, insereResultadoDTO);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/competicao/{competicaoId}/dono/{donoId}")
	public ResponseEntity<ResultadoDTO> buscaResultado(@PathVariable Long competicaoId, @PathVariable Long donoId) {
		ResultadoDTO resultado = service.buscaResultado(competicaoId, donoId);
		return ResponseEntity.ok(resultado)	;
	}
}
