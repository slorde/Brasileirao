package com.fsoft.brasileirao.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.brasileirao.dto.DonoDTO;
import com.fsoft.brasileirao.service.DonoService;

@CrossOrigin
@RestController
@RequestMapping("donos")
public class DonoController {
	
	@Autowired
	private DonoService service;
	
	@GetMapping
	public ResponseEntity<List<DonoDTO>> listaDonos() {
		List<DonoDTO> donos = service.findAll().stream().map(dono -> new DonoDTO(dono)).collect(Collectors.toList());
		return ResponseEntity.ok(donos);
	}
	
	@PostMapping
	public ResponseEntity<List<DonoDTO>> criaDono(@RequestBody DonoDTO donoDto) {
		service.create(donoDto.getNome());
		return ResponseEntity.noContent().build();
	}
}
