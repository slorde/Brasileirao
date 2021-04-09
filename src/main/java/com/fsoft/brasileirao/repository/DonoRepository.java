package com.fsoft.brasileirao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.brasileirao.model.Dono;

@Repository
public interface DonoRepository extends JpaRepository<Dono, Long> {

	@Transactional(readOnly = true)
	Dono findByIsResultadoTrue();

	@Transactional(readOnly = true)
	Dono findByNome(String nome);
	
}
