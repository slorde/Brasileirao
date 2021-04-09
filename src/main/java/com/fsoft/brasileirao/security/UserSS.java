package com.fsoft.brasileirao.security;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fsoft.brasileirao.model.enums.Perfil;

import lombok.Getter;

@Getter
public class UserSS implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public UserSS() {
	}

	public UserSS(Long id, String login, String senha, Set<Perfil> perfis) {
		super();
		this.id = id;
		this.username = login;
		this.password = senha;
		this.authorities = perfis.stream().map(perfil -> new SimpleGrantedAuthority(perfil.getDescricao())).collect(Collectors.toList());
	}

	public boolean hasRole(Perfil admin) {
		return authorities.contains(new SimpleGrantedAuthority(admin.getDescricao()));
	}
}
