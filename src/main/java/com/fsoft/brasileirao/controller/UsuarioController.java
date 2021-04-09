package com.fsoft.brasileirao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsoft.brasileirao.dto.UsuarioDTO;
import com.fsoft.brasileirao.service.UsuarioService;

@CrossOrigin
@RestController
@RequestMapping("usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@PostMapping
	public ResponseEntity<Void> criaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		service.create(usuarioDTO.getLogin(), usuarioDTO.getSenha(), usuarioDTO.getDonoId(), usuarioDTO.isAdmin());
		return ResponseEntity.noContent().build();
	}
}
