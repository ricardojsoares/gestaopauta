package com.gestaopauta.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestaopauta.entity.MensagemRetorno;
import com.gestaopauta.entity.Pauta;
import com.gestaopauta.repository.PautaRepository;
import com.gestaopauta.resources.Validacoes;

@Service
public class PautaServiceImpl implements PautaService{

	@Autowired
	PautaRepository pautaRepository;
	
	// Permite inserir uma nova pauta
	@Override
	public ResponseEntity<?> create(Pauta pauta) {
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
	@Override
	public List<Pauta> findAll() {
		return pautaRepository.findAll();
	}
	
	// Retorna as informações da pauta
	@Override
	public ResponseEntity<?> findById(long id) {
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
