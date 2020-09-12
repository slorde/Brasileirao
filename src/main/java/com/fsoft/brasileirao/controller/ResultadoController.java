package com.fsoft.brasileirao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.brasileirao.service.ResultadoService;

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
	
}
