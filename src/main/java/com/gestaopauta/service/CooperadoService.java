package com.gestaopauta.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gestaopauta.entity.Cooperado;

public interface CooperadoService {
	public ResponseEntity<?> create(Cooperado coop);
	public List<Cooperado> findAll();
	public ResponseEntity<?> findById(long id);
	public ResponseEntity<?> findStatusCooperadoById(long id);
	public ResponseEntity<?> findStatusCooperadoByCpf(String cpf);
	public ResponseEntity<Cooperado> habilitarParaVoto(long id);
	public ResponseEntity<Cooperado> desabilitarParaVoto(long id);
	public ResponseEntity<?> update(long id, Cooperado cooperado);
	public ResponseEntity<?> delete(long id); 
}
