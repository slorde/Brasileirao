package com.fsoft.brasileirao.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.model.Dono;
import com.fsoft.brasileirao.repository.DonoRepository;

@Service
public class DonoService {

	@Autowired
	private DonoRepository repository;
	
	public Dono findResultado() {
		return repository.findByIsResultadoTrue();
	}

	@Transactional
	public Dono create(String nome) {
		return repository.save(new Dono(nome));
	}
}
