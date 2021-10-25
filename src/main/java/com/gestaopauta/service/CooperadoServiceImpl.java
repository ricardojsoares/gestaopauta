package com.gestaopauta.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.gestaopauta.entity.Cooperado;
import com.gestaopauta.entity.MensagemRetorno;
import com.gestaopauta.entity.dto.CooperadoStatusDto;
import com.gestaopauta.repository.CooperadoRepository;
import com.gestaopauta.resources.EnumStatusCooperado;
import com.gestaopauta.resources.Validacoes;

@Service
public class CooperadoServiceImpl implements CooperadoService {

	@Autowired
	CooperadoRepository coopRepository;

	@Autowired
	ValidaCpfService validaCpfService;

	// Permite inserir um novo cooperado
	@Override
	public ResponseEntity<?> create(Cooperado coop) {
		String retornoValidacao = validaDadosCooperado(coop);

		if (!retornoValidacao.equals("")) {
			return ResponseEntity.status(406).body(new MensagemRetorno(retornoValidacao));
		}

		Optional<Cooperado> cooperadoCpf = coopRepository.findByCpf(coop.getCpf());
		if (cooperadoCpf.isPresent()) {
			return ResponseEntity.status(406).body(new MensagemRetorno(
					"Operação não permitida, já existe um cooperado cadastrado para o CPF " + coop.getCpf()));
		} else {
			coop.setStatus(validaCpfService.validaCpf(coop.getCpf()));
			return ResponseEntity.ok().body(coopRepository.save(coop));
		}
	}

	// Retorna a lista de todos os cooperados
	@Override
	public List<Cooperado> findAll() {
		return coopRepository.findAll();
	}

	// Retorna as informações do cooperado
	@Override
	public ResponseEntity<?> findById(long id) {
		return coopRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Recebe o Id do cooperado e retorna o seu status
	@Override
	public ResponseEntity<?> findStatusCooperadoById(long id) {
		return coopRepository.findById(id).map(record -> {
			return ResponseEntity.ok().body(new CooperadoStatusDto(record.getStatus()));
		}).orElse(ResponseEntity.notFound().build());
	}

	// Recebe o CPF do cooperado e retorna o seu status
	@GetMapping(value = "/getStatusCooperadoPorCpf/{cpf}")
	public ResponseEntity<?> findStatusCooperadoByCpf(@PathVariable String cpf) {
		return coopRepository.findByCpf(cpf).map(record -> {
			return ResponseEntity.ok().body(new CooperadoStatusDto(record.getStatus()));
		}).orElse(ResponseEntity.notFound().build());
	}

	// Serve para habilitar o cooperado para realização de votos
	@Override
	public ResponseEntity<Cooperado> habilitarParaVoto(long id) {
		return coopRepository.findById(id).map(record -> {
			record.setStatus(EnumStatusCooperado.ABLE_TO_VOTE);
			return ResponseEntity.ok().body(coopRepository.save(record));
		}).orElse(ResponseEntity.notFound().build());
	}

	// Serve para desabilitar o cooperado para realização de votos
	@Override
	public ResponseEntity<Cooperado> desabilitarParaVoto(long id) {
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
//		} else if (!Validacoes.isCPF(cooperado.getCpf())) {
//			retorno = "O CPF informado não corresponde a um CPF válido";
//		}

		if (!Validacoes.validaString(cooperado.getNome())) {
			retorno = retorno + ((retorno.equals("")) ? "" : ", ") + "O nome não pode ser vazio";
		}
		return retorno;
	}

	@Override
	public ResponseEntity<?> update(long id, Cooperado cooperado) {
		String retornoValidacao = validaDadosCooperado(cooperado);

		if (!retornoValidacao.equals("")) {
			return ResponseEntity.status(406).body(new MensagemRetorno(retornoValidacao));
		}

		Optional<Cooperado> cooperadoCpf = coopRepository.findById(cooperado.getId());
		if (cooperadoCpf.isPresent()) {
			cooperado.setStatus(validaCpfService.validaCpf(cooperado.getCpf()));
			return ResponseEntity.ok().body(coopRepository.save(cooperado));
		} else {
			return ResponseEntity.status(404)
					.body(new MensagemRetorno("Não foi encontrado nenhum cooperado para o ID informado"));
		}
	}

	@Override
	public ResponseEntity<?> delete(long id) {
		Optional<Cooperado> cooperado = coopRepository.findById(id);
		if (cooperado.isPresent()) {
			coopRepository.delete(cooperado.get());
			return ResponseEntity.ok().body(new MensagemRetorno("Cooperado excluído com sucesso"));
		} else {
			return ResponseEntity.status(404)
					.body(new MensagemRetorno("Não foi encontrado nenhum cooperado para o ID informado"));
		}
	}
}
