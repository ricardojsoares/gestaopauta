/**
 * Autor: Ricardo Soares
 */
package com.gestaopauta.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestaopauta.model.entity.Cooperado;
import com.gestaopauta.model.entity.CooperadoStatus;
import com.gestaopauta.model.entity.MensagemRetorno;
import com.gestaopauta.model.repository.CooperadoRepository;
import com.gestaopauta.model.resources.EnumStatusCooperado;
import com.gestaopauta.model.resources.Validacoes;

@RestController
@RequestMapping("/gestaopauta/cooperado")
public class CooperadoController {

	@Autowired
	CooperadoRepository coopRepository;

	// Permite inserir um novo cooperado
	@PostMapping
	public ResponseEntity<?> create(@RequestBody Cooperado coop) {
		String retornoValidacao = validaDadosCooperado(coop);

		if (!retornoValidacao.equals("")) {
			return ResponseEntity.status(406).body(new MensagemRetorno(retornoValidacao));
		}

		if(coop.getId() > 0) {
			return ResponseEntity.status(406).body(new MensagemRetorno("O Id do cooperado não deve ser informado"));
		}
		
		Optional<Cooperado> cooperadoCpf = coopRepository.findByCpf(coop.getCpf());
		if(cooperadoCpf.isPresent()) {
			return ResponseEntity.status(406).body(new MensagemRetorno("Operação não permitida, já existe um cooperado cadastrado para o CPF " + coop.getCpf()));
		} else {
			return ResponseEntity.ok().body(coopRepository.save(coop));	
		}
	}

	// Retorna a lista de todos os cooperados
	@GetMapping
	public List<Cooperado> findAll() {
		return coopRepository.findAll();
	}

	// Retorna as informações do cooperado
	@GetMapping(value = "{id}")
	public ResponseEntity<?> findById(@PathVariable long id) {
		return coopRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Recebe o Id do cooperado e retorna o seu status
	@GetMapping(value = "/getStatusCooperadoPorId/{id}")
	public ResponseEntity<?> findStatusCooperadoById(@PathVariable long id) {
		return coopRepository.findById(id)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Recebe o CPF do cooperado e retorna o seu status
	@GetMapping(value = "/getStatusCooperadoPorCpf/{cpf}")
	public ResponseEntity<?> findStatusCooperadoByCpf(@PathVariable String cpf) {
		return coopRepository.findByCpf(cpf)
				.map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}
	
	// Serve para habilitar o cooperado para realização de votos
	@PutMapping("/habilitarParaVoto/{id}")
	public ResponseEntity<Cooperado> habilitarParaVoto(@PathVariable long id) {
		return coopRepository.findById(id).map(record -> {
			record.setStatus(EnumStatusCooperado.ABLE_TO_VOTE);
			return ResponseEntity.ok().body(coopRepository.save(record));
		}).orElse(ResponseEntity.notFound().build());
	}

	// Serve para desabilitar o cooperado para realização de votos
	@PutMapping("/desabilitarParaVoto/{id}")
	public ResponseEntity<Cooperado> desabilitarParaVoto(@PathVariable long id) {
		return coopRepository.findById(id).map(record -> {
			record.setStatus(EnumStatusCooperado.UNABLE_TO_VOTE);
			return ResponseEntity.ok().body(coopRepository.save(record));
		}).orElse(ResponseEntity.notFound().build());
	}

	// Método responsável por fazer a validação do objeto Cooperado
	private String validaDadosCooperado(Cooperado cooperado) {
		String retorno = "";

		if (!Validacoes.validaString(cooperado.getCpf())) {
			retorno = "CPF não pode ser vazio";
		} else if (!cooperado.getCpf().matches("\\d{11}")) {
			retorno = "O CPF deve possuir apenas números e ter 11 caractéres";
		}
		
		if (!Validacoes.validaString(cooperado.getNome())) {
			retorno = retorno + ((retorno.equals(""))? "" : ", ") + "O nome não pode ser vazio";
		}
		return retorno;
	}
}