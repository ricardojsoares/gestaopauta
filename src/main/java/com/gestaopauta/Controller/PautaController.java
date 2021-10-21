/**
 * Autor: Ricardo Soares
 * Data: 16/10/2021
 * **/

package com.gestaopauta.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gestaopauta.entity.Pauta;
import com.gestaopauta.entity.dto.PautaPostDto;
import com.gestaopauta.service.PautaService;

@RestController
@RequestMapping("/gestaopauta/pauta")
public class PautaController {

	@Autowired
	PautaService pautaService;
	
	// Permite inserir uma nova pauta
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody PautaPostDto pauta) {
		return pautaService.create(new Pauta(pauta.getTitulo(), pauta.getDescricao()));	
	}
	
	// Retorna a lista de todas as pautas
	@GetMapping
	public List<Pauta> findAll() {
		return pautaService.findAll();
	}
	
	// Retorna as informações da pauta
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return pautaService.findById(id);
	}
}
