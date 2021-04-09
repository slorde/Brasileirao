package com.fsoft.brasileirao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.fsoft.brasileirao.model.Competicao;
import com.fsoft.brasileirao.model.Dono;
import com.fsoft.brasileirao.model.Resultado;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long>{
	@Transactional(readOnly = true)
	Resultado findByCompeticaoAndDono(Competicao competicao, Dono dono);
}
