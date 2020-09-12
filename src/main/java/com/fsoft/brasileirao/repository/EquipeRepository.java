package com.fsoft.brasileirao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.brasileirao.model.Equipe;

@Repository
public interface EquipeRepository extends JpaRepository<Equipe, Long>{

	@Transactional(readOnly = true)
	Equipe findByNome(String nome);
}
