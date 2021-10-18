package com.gestaopauta.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestaopauta.model.entity.Cooperado;

public interface CooperadoRepository extends JpaRepository<Cooperado, Long>{
	public Optional<Cooperado> findByCpf(String cpf);
}
