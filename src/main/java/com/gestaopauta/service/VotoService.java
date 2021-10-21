package com.gestaopauta.service;

import org.springframework.http.ResponseEntity;

import com.gestaopauta.entity.dto.VotoPostDto;

public interface VotoService {
	public ResponseEntity<?> votar(VotoPostDto votoDto);
}
