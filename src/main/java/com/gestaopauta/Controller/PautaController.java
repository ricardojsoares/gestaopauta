/**
 * Autor: Ricardo Soares
 * Data: 16/10/2021
 * **/

package com.gestaopauta.Controller;

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

import com.gestaopauta.model.entity.MensagemRetorno;
import com.gestaopauta.model.entity.Pauta;
import com.gestaopauta.model.repository.PautaRepository;
import com.gestaopauta.model.resources.Validacoes;

@RestController
@RequestMapping("/gestaopauta/pauta")
public class PautaController {

	@Autowired
	PautaRepository pautaRepository;

	// Permite inserir uma nova pauta
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> create(@RequestBody Pauta pauta) {
		String retornoValidacao = validaDadosPauta(pauta);

		if (!retornoValidacao.equals("")) {
			return ResponseEntity.status(406).body(new MensagemRetorno(retornoValidacao));
		}

		if(pauta.getId() > 0) {
			return ResponseEntity.status(406).body(new MensagemRetorno("O Id da pauta não deve ser informado"));
		}
		
		return ResponseEntity.ok().body(pautaRepository.save(pauta));	
	}
	
	// Retorna a lista de todas as pautas
	@GetMapping
	public List<Pauta> findAll() {
		return pautaRepository.findAll();
	}
	
	// Retorna as informações da pauta
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return pautaRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// Método responsável por fazer a validação do objeto Pauta
	private String validaDadosPauta(Pauta pauta) {
		String retorno = "";

		if (!Validacoes.validaString(pauta.getTitulo())) {
			retorno = "Título não pode ser vazio";
		}

		if (!Validacoes.validaString(pauta.getDescricao())) {
			retorno = retorno + ((retorno.equals(""))? "": ", ") + "Descrição não pode ser vazia";
		}
		return retorno;
	}
}
