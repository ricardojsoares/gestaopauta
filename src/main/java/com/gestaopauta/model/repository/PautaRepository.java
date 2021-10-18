package com.gestaopauta.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestaopauta.model.entity.Pauta;

public interface PautaRepository extends JpaRepository<Pauta, Long>{

}
