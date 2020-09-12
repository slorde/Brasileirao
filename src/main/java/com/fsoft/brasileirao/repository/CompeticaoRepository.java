package com.fsoft.brasileirao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.brasileirao.model.Competicao;

@Repository
public interface CompeticaoRepository extends JpaRepository<Competicao, Long>{

	@Transactional(readOnly = true)
	List<Competicao> findByFinalizadaFalse();
}
