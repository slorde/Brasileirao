package com.fsoft.brasileirao.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.model.Equipe;
import com.fsoft.brasileirao.repository.EquipeRepository;

@Service
public class EquipeService {

	@Autowired
	private EquipeRepository repository;

	@Transactional
	public Equipe findOrCreate(String nome) {
		Equipe equipe = repository.findByNome(nome);
		
		if (equipe == null) {
			equipe = new Equipe(nome);
			repository.save(equipe);
		}
		
		return equipe;
	}
}
