package com.fsoft.brasileirao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.model.Usuario;
import com.fsoft.brasileirao.repository.UsuarioRepository;
import com.fsoft.brasileirao.security.UserSS;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Usuario usuario = repository.findByLogin(login);
		
		if (usuario == null)
			throw new UsernameNotFoundException(login);
		
		return new UserSS(usuario.getId(), usuario.getLogin(), usuario.getSenha(), usuario.getDonoId(), usuario.getPerfis());
	}

}
