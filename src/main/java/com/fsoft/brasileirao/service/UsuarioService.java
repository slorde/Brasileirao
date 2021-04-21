package com.fsoft.brasileirao.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.model.Dono;
import com.fsoft.brasileirao.model.Usuario;
import com.fsoft.brasileirao.model.enums.Perfil;
import com.fsoft.brasileirao.repository.DonoRepository;
import com.fsoft.brasileirao.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	@Autowired
	private DonoRepository donoRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Transactional
	public Usuario create(String login, String senha, Integer donoId, Boolean admin) {
		Usuario usuario= repository.findByLogin(login);
		
		if (usuario == null) {
			usuario = new Usuario(login, passwordEncoder.encode(senha));
		}
		
		if (admin) usuario.addPerfi(Perfil.ADMIN);
		
		if (donoId != null) {
			Dono dono = donoRepository.findById(donoId.longValue()).orElseThrow();
			usuario.setDono(dono);
		}
		
		repository.save(usuario);
		
		return usuario;
	}
}
