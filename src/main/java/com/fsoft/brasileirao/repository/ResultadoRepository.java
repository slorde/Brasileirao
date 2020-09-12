package com.fsoft.brasileirao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.brasileirao.model.Resultado;

@Repository
public interface ResultadoRepository extends JpaRepository<Resultado, Long>{

}
