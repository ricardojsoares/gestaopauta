package com.gestaopauta.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.gestaopauta.entity.SessaoVotacao;
import com.gestaopauta.entity.dto.SessaoVotacaoPostDto;

public interface SessaoVotacaoService {
	public ResponseEntity<?> create(SessaoVotacaoPostDto sessaoVotacao);
	public List<SessaoVotacao> findAll();
	public ResponseEntity<?> findById(long id);
	public ResponseEntity<?> openToVote(long id);
	public ResponseEntity<?> performCalculation(long id);
}
