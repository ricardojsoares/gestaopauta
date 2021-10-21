/**
 * Autor: Ricardo Soares
 * Data: 18/10/2021
 * **/

package com.gestaopauta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestaopauta.entity.SessaoVotacao;

public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long>{

}
