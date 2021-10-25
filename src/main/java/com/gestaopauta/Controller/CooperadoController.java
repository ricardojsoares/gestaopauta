/**
 * Autor: Ricardo Soares
 * Data: 16/10/2021
 * **/
package com.gestaopauta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gestaopauta.entity.Cooperado;
import com.gestaopauta.entity.MensagemRetorno;
import com.gestaopauta.entity.dto.CooperadoPostDto;
import com.gestaopauta.resources.Validacoes;
import com.gestaopauta.service.CooperadoService;

@RestController
@RequestMapping("/gestaopauta/cooperado")
public class CooperadoController {

	@Autowired
	CooperadoService coopService;

	// Permite inserir um novo cooperado
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody CooperadoPostDto coop) {
		return coopService.create(new Cooperado(coop.getCpf(), coop.getNome()));
	}

	// Retorna a lista de todos os cooperados
	@GetMapping
	public List<Cooperado> findAll() {
		return coopService.findAll();
	}

	// Retorna as informações do cooperado
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@RequestHeader String versionApi, @PathVariable long id) {
		if (Validacoes.validaString(versionApi)) { 
			switch (versionApi) {
			case "v1":
				return coopService.findById(id);
			default:
				return ResponseEntity.status(401).body(new MensagemRetorno("Informe uma versão válida da API"));
			}
		} else {
			return ResponseEntity.status(401).body(new MensagemRetorno("Informe a versão da API"));
		}
	}

	// Recebe o Id do cooperado e retorna o seu status
	@GetMapping(value = "/getStatusCooperadoPorId/{id}")
	public ResponseEntity<?> findStatusCooperadoById(@PathVariable long id) {
		return coopService.findStatusCooperadoById(id);
	}

	// Recebe o CPF do cooperado e retorna o seu status
	@GetMapping(value = "/getStatusCooperadoPorCpf/{cpf}")
	public ResponseEntity<?> findStatusCooperadoByCpf(@PathVariable String cpf) {
		return coopService.findStatusCooperadoByCpf(cpf);
	}

	// Serve para habilitar o cooperado para realização de votos
	@PutMapping("/habilitarParaVoto/{id}")
	public ResponseEntity<Cooperado> habilitarParaVoto(@PathVariable long id) {
		return coopService.habilitarParaVoto(id);
	}

	// Serve para desabilitar o cooperado para realização de votos
	@PutMapping("/desabilitarParaVoto/{id}")
	public ResponseEntity<Cooperado> desabilitarParaVoto(@PathVariable long id) {
		return coopService.desabilitarParaVoto(id);
	}
	
	// Permite inserir um novo cooperado
	@PutMapping("{id}")
	public ResponseEntity<?> update(@PathVariable long id, @RequestBody Cooperado cooperado) {
		return coopService.update(id, cooperado);
	}
	
	// Permite inserir um novo cooperado
	@DeleteMapping("{id}")
	public ResponseEntity<?> delete(@PathVariable long id) {
		return coopService.delete(id);
	}
}