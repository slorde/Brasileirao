package com.fsoft.brasileirao.model;

import static com.fsoft.brasileirao.model.enums.Perfil.CLIENTE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fsoft.brasileirao.model.enums.Perfil;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String login;
	private String senha;
	
	@OneToMany(mappedBy = "usuario")
	@JsonIgnore
	private List<Dono> jogadores = new ArrayList<>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	public Usuario(String login, String senha) {
		this.login = login;
		this.senha = senha;
		addPerfi(CLIENTE);
	}
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(p -> Perfil.toEnum(p)).collect(Collectors.toSet());
	}
	
	public void addPerfi(Perfil perfil) {
		perfis.add(perfil.getCodigo());
	}
	
	public void addDono(Dono dono) {
		this.jogadores.add(dono);
	}
}
