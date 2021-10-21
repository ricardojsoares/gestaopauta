package com.gestaopauta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestaopauta.entity.dto.VotoPostDto;
import com.gestaopauta.service.VotoService;

@RestController
@RequestMapping("/gestaopauta/voto")
public class VotoController {

	@Autowired
	VotoService votoService;
	
	@PostMapping
	public ResponseEntity<?> votar(@RequestBody VotoPostDto votoDto){
		return votoService.votar(votoDto);
	}
}
