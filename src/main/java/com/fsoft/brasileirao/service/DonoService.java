package com.fsoft.brasileirao.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fsoft.brasileirao.dto.UpdateDonoDTO;
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
	
	public Dono findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Dono não encontrado pelo id: " + id));
	}
	
	public List<Dono> findAll() {
		return repository.findAll();
	}

	public void update(UpdateDonoDTO updateDto) {
		Dono dono = repository.findById(updateDto.getId()).orElseThrow(() -> new RuntimeException("não achou dono"));
		dono.setNome(updateDto.getNome());
		dono.setIsResultado(updateDto.getIsResultado());
		repository.save(dono);
	}
}
