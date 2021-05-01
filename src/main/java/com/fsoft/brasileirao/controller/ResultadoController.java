package com.fsoft.brasileirao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.brasileirao.dto.InsereResultadoDTO;
import com.fsoft.brasileirao.dto.ResultadoDTO;
import com.fsoft.brasileirao.model.Resultado;
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
	
	@GetMapping("/competicao/{competicaoId}/dono")
	public ResponseEntity<ResultadoDTO> buscaResultado(@PathVariable Long competicaoId) {
		ResultadoDTO resultado = service.buscaResultado(competicaoId);
		return ResponseEntity.ok(resultado)	;
	}
	
	@GetMapping("/ano/{ano}")
	public ResponseEntity<List<Resultado>> resultadoRaw(@PathVariable Integer ano) {
		List<Resultado> resultados = service.resultadoRaw(ano);
		return ResponseEntity.ok(resultados);
	}
	
	@DeleteMapping("/{resultadoId}/ano/{ano}")
	public ResponseEntity<List<Resultado>> resultadoRaw(@PathVariable Long resultadoId, @PathVariable Integer ano) {
		service.delete(ano,resultadoId);
		return ResponseEntity.noContent().build();
	}
}
