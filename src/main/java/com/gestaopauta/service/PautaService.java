package com.gestaopauta.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gestaopauta.entity.Pauta;

public interface PautaService {
	public ResponseEntity<?> create(Pauta pauta);
	public List<Pauta> findAll();
	public ResponseEntity<?> findById(long id);
}
