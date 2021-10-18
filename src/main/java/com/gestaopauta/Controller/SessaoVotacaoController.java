/**
 * Autor: Ricardo Soares
 * Data: 18/10/2021
 * **/

package com.gestaopauta.Controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gestaopauta.model.entity.MensagemRetorno;
import com.gestaopauta.model.entity.SessaoVotacao;
import com.gestaopauta.model.repository.SessaoVotacaoRepository;

@RestController
@RequestMapping("/gestaopauta/sessaoVotacao")
public class SessaoVotacaoController {

	@Autowired
	SessaoVotacaoRepository sessaoVotacaoRepository;

	// Permite inserir uma nova sessão de votação
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody SessaoVotacao sessaoVotacao) {
		if(sessaoVotacao.getId() > 0) {
			return ResponseEntity.status(406).body(new MensagemRetorno("O Id da sessão de votação não deve ser informado"));
		}
		
		if (sessaoVotacao.getTempo() == 0) {
			sessaoVotacao.setTempo(1);
		}
		return ResponseEntity.ok().body(sessaoVotacaoRepository.save(sessaoVotacao));	
	}
	
	// Retorna uma lista com todas as sessões de votação cadastradas
	@GetMapping
	public List<SessaoVotacao> findAll() {
		return sessaoVotacaoRepository.findAll();
	}
	
	// Retorna as informações da sessão de votação
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return sessaoVotacaoRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// Permite realizar a abertura da sessão de votação
	@PutMapping("/abrirVotacao/{id}")
	public ResponseEntity<?> openToVote(@PathVariable long id) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(id);
		
		if (!sessaoVotacao.isPresent()) {
			return ResponseEntity.notFound().build();	
		} else {
			if (sessaoVotacao.get().getDatahoraInicio() == null) {
				sessaoVotacao.get().setDatahoraInicio(LocalDateTime.now());
				return ResponseEntity.ok().body(sessaoVotacaoRepository.save(sessaoVotacao.get()));
			} else {
				return ResponseEntity.ok().body(new MensagemRetorno("A sessão de votação já tinha sido iniciada às " + sessaoVotacao.get().getDatahoraInicio()));
			}
		}	
	}
	
	// Retorna uma lista com todas as sessões de votação cadastradas
	@GetMapping("/calcularTempo/{id}")
	public ResponseEntity<?> CalcularTempoVotacao(@PathVariable long id) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(id);
		
		long Tempo = sessaoVotacao.get().getDatahoraInicio().until(LocalDateTime.now(), ChronoUnit.MINUTES);
		
		return ResponseEntity.ok().body(new MensagemRetorno("O tempo de votação atual é: " + Tempo));
	}
}