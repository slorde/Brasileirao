package com.fsoft.brasileirao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fsoft.brasileirao.model.Classificacao;

@Repository
public interface ClassificacaoRepository extends JpaRepository<Classificacao, Long>{

}
