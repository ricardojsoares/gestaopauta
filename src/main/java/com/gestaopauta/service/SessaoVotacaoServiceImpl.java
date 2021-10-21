package com.gestaopauta.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestaopauta.entity.MensagemRetorno;
import com.gestaopauta.entity.Pauta;
import com.gestaopauta.entity.SessaoVotacao;
import com.gestaopauta.entity.Voto;
import com.gestaopauta.entity.dto.PautaResponseDto;
import com.gestaopauta.entity.dto.SessaoVotacaoPostDto;
import com.gestaopauta.repository.PautaRepository;
import com.gestaopauta.repository.SessaoVotacaoRepository;
import com.gestaopauta.resources.EnumStatusPauta;

@Service
public class SessaoVotacaoServiceImpl implements SessaoVotacaoService {

	@Autowired
	SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Autowired
	PautaRepository pautaRepository;

	// Permite inserir uma nova sessão de votação
	@Override
	public ResponseEntity<?> create(SessaoVotacaoPostDto sessaoVotacaoDto) {

		if (sessaoVotacaoDto.getTempo() == 0) {
			sessaoVotacaoDto.setTempo(1);
		}
		
		if (sessaoVotacaoDto.getIdPauta() == 0) {
			return ResponseEntity.ok().body(new MensagemRetorno("É necessário informar o ID da pauta"));
		}
		
		Optional<Pauta> pauta = pautaRepository.findById(sessaoVotacaoDto.getIdPauta());
		
		if (!pauta.isPresent()) {
			return ResponseEntity.ok().body(new MensagemRetorno("Não foi encontrada nenhuma pauta para o ID informado"));			
		}
		
		if (pauta.get().getStatus() != EnumStatusPauta.NONE) {
			return ResponseEntity.ok().body(new MensagemRetorno("Esta pauta já foi votada"));			
		}
		
		return ResponseEntity.ok().body(sessaoVotacaoRepository.save(new SessaoVotacao(sessaoVotacaoDto.getTempo(), pauta.get())));
	}

	// Retorna uma lista com todas as sessões de votação cadastradas
	@Override
	public List<SessaoVotacao> findAll() {
		return sessaoVotacaoRepository.findAll();
	}

	// Retorna as informações da sessão de votação
	@Override
	public ResponseEntity<?> findById(long id) {
		return sessaoVotacaoRepository.findById(id).map(record -> ResponseEntity.ok().body(record))
				.orElse(ResponseEntity.notFound().build());
	}

	// Realiza a contagem dos votos e determina o status da pauta
	@Override
	public ResponseEntity<?> performCalculation(long id) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(id);
		
		if (!sessaoVotacao.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		int votosSim = 0;
		int votosNao = 0;
		
		for (Voto voto: sessaoVotacao.get().getVotos()) {
			if (voto.getOpcao().equals("S")) {
				votosSim++;
			} else {
				votosNao++;
			}
		}
		
		if (votosSim == votosNao) {
			sessaoVotacao.get().getPauta().setStatus(EnumStatusPauta.TIED);
		} else {
			sessaoVotacao.get().getPauta().setStatus((votosSim > votosNao)? EnumStatusPauta.APPROVED:EnumStatusPauta.REJECTED);
		}
		
		Pauta pauta = pautaRepository.save(sessaoVotacao.get().getPauta());
		return ResponseEntity.ok().body(new PautaResponseDto(pauta.getId(), (votosSim + votosNao), votosSim, votosNao, pauta.getStatus()));
	}
	
	// Permite realizar a abertura da sessão de votação
	@Override
	public ResponseEntity<?> openToVote(long id) {
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(id);

		if (!sessaoVotacao.isPresent()) {
			return ResponseEntity.notFound().build();
		} else {
			if (sessaoVotacao.get().getDatahoraInicio() == null) {
				sessaoVotacao.get().setDatahoraInicio(LocalDateTime.now());
				return ResponseEntity.ok().body(sessaoVotacaoRepository.save(sessaoVotacao.get()));
			} else {
				return ResponseEntity.ok().body(new MensagemRetorno(
						"A sessão de votação já tinha sido iniciada às " + sessaoVotacao.get().getDatahoraInicio()));
			}
		}
	}

}
