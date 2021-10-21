/**
 * Autor: Ricardo Soares
 * Data: 18/10/2021
 * **/

package com.gestaopauta.controller;

import java.util.List;

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

import com.gestaopauta.entity.SessaoVotacao;
import com.gestaopauta.entity.dto.SessaoVotacaoPostDto;
import com.gestaopauta.service.SessaoVotacaoService;

@RestController
@RequestMapping("/gestaopauta/sessaoVotacao")
public class SessaoVotacaoController {

	@Autowired
	SessaoVotacaoService sessaoVotacaoService;

	// Permite inserir uma nova sessão de votação
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody SessaoVotacaoPostDto sessaoVotacao) {
		return sessaoVotacaoService.create(sessaoVotacao);	
	}
	
	// Retorna uma lista com todas as sessões de votação cadastradas
	@GetMapping
	public List<SessaoVotacao> findAll() {
		return sessaoVotacaoService.findAll();
	}
	
	// Retorna as informações da sessão de votação
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return sessaoVotacaoService.findById(id);
	}
	
	// Permite realizar a abertura da sessão de votação
	@PutMapping("/abrirVotacao/{id}")
	public ResponseEntity<?> openToVote(@PathVariable long id) {
		return sessaoVotacaoService.openToVote(id);	
	}
	
	// Permite realizar a abertura da sessão de votação
	@PutMapping("/apuracaoVotacao/{id}")
	public ResponseEntity<?> performCalculation(@PathVariable long id) {
		return sessaoVotacaoService.performCalculation(id);	
	}
}