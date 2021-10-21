package com.gestaopauta.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.gestaopauta.entity.Cooperado;
import com.gestaopauta.entity.MensagemRetorno;
import com.gestaopauta.entity.SessaoVotacao;
import com.gestaopauta.entity.Voto;
import com.gestaopauta.entity.dto.VotoPostDto;
import com.gestaopauta.entity.dto.VotoResponseDto;
import com.gestaopauta.repository.CooperadoRepository;
import com.gestaopauta.repository.SessaoVotacaoRepository;
import com.gestaopauta.repository.VotoRepository;
import com.gestaopauta.resources.EnumStatusCooperado;
import com.gestaopauta.resources.EnumStatusPauta;

@Service
public class VotoServiceImpl implements VotoService{

	@Autowired
	VotoRepository votoRepository;
	
	@Autowired
	CooperadoRepository coopRepository;
	
	@Autowired
	SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@Override
	public ResponseEntity<?> votar(VotoPostDto votoDto) {
		if (votoDto.getOpcao().equalsIgnoreCase("S") && votoDto.getOpcao().equalsIgnoreCase("N")) {
			return ResponseEntity.status(406).body(new MensagemRetorno("A opção informada deve ser S-Sim ou N-Não"));
		}
		
		Optional<Cooperado> cooperado = coopRepository.findById(votoDto.getIdCooperado());
		
		if (!cooperado.isPresent()) {
			return ResponseEntity.status(406).body(new MensagemRetorno("Cooperado não encontrado"));
		} else if (cooperado.get().getStatus() == EnumStatusCooperado.UNABLE_TO_VOTE){
			return ResponseEntity.status(406).body(new MensagemRetorno("Cooperado não está habilitado para votar"));
		}
		
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(votoDto.getIdSessaoVotacao());
		
		if (!sessaoVotacao.isPresent()) {
			return ResponseEntity.status(406).body(new MensagemRetorno("Sessão de votação não encontrada"));
		} else if (sessaoVotacao.get().getDatahoraInicio() == null ) {
			return ResponseEntity.status(406).body(new MensagemRetorno("Sessão de votação ainda não foi iniciada"));
		}
		
		if (sessaoVotacao.get().getDatahoraInicio().until(LocalDateTime.now(), ChronoUnit.MINUTES) > sessaoVotacao.get().getTempo()) {
			return ResponseEntity.status(406).body(new MensagemRetorno("O tempo da sessão de votação expirou"));
		}
		
		if (sessaoVotacao.get().getPauta().getStatus() != EnumStatusPauta.NONE) {
			return ResponseEntity.ok().body(new MensagemRetorno("A apuração de votos dessa pauta já foi realizado"));			
		}

		for (Voto v: sessaoVotacao.get().getVotos()) {
			if (v.getCooperado().getId() == cooperado.get().getId()) {
				return ResponseEntity.status(406).body(new MensagemRetorno("O cooperado " + cooperado.get().getNome() + " já realizou o seu voto"));		
			}
		}
		
		Voto votoRealizado = votoRepository.save(new Voto(votoDto.getOpcao(), cooperado.get(), sessaoVotacao.get()));
		VotoResponseDto votoResponseDto = new VotoResponseDto(votoRealizado.getId(), votoRealizado.getDataHoraCriacao(), "Voto realizado com sucesso");

		return ResponseEntity.ok().body(votoResponseDto);
	}

}
