package com.gestaopauta.Controller;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestaopauta.model.entity.Cooperado;
import com.gestaopauta.model.entity.MensagemRetorno;
import com.gestaopauta.model.entity.SessaoVotacao;
import com.gestaopauta.model.entity.Voto;
import com.gestaopauta.model.repository.CooperadoRepository;
import com.gestaopauta.model.repository.SessaoVotacaoRepository;
import com.gestaopauta.model.repository.VotoRepository;
import com.gestaopauta.model.resources.EnumStatusCooperado;

@RestController
@RequestMapping("/gestaopauta/voto")
public class VotoController {

	@Autowired
	VotoRepository votoRepository;
	
	@Autowired
	CooperadoRepository coopRepository;
	
	@Autowired
	SessaoVotacaoRepository sessaoVotacaoRepository;
	
	@PostMapping
	public ResponseEntity<?> votar(@RequestBody Voto voto){
		if(voto.getId() > 0) {
			return ResponseEntity.status(406).body(new MensagemRetorno("O Id do voto não deve ser informado"));
		}
		
		if (voto.getOpcao().equalsIgnoreCase("S") && voto.getOpcao().equalsIgnoreCase("N")) {
			return ResponseEntity.status(406).body(new MensagemRetorno("A opção informada deve ser S-Sim ou N-Não"));
		}
		
		Optional<Cooperado> cooperado = coopRepository.findById(voto.getCooperado().getId());
		
		if (!cooperado.isPresent()) {
			return ResponseEntity.status(406).body(new MensagemRetorno("Cooperado não encontrado"));
		} else if (cooperado.get().getStatus() == EnumStatusCooperado.UNABLE_TO_VOTE){
			return ResponseEntity.status(406).body(new MensagemRetorno("Cooperado não está habilitado para votar"));
		}
		
		Optional<SessaoVotacao> sessaoVotacao = sessaoVotacaoRepository.findById(voto.getSessaoVotacao().getId());
		
		if (!sessaoVotacao.isPresent()) {
			return ResponseEntity.status(406).body(new MensagemRetorno("Sessão de votação não encontrada"));
		} else if (sessaoVotacao.get().getDatahoraInicio() == null ) {
			return ResponseEntity.status(406).body(new MensagemRetorno("Sessão de votação ainda não foi iniciada"));
		}
		
		if (sessaoVotacao.get().getDatahoraInicio().until(LocalDateTime.now(), ChronoUnit.MINUTES) > sessaoVotacao.get().getTempo()) {
			return ResponseEntity.status(406).body(new MensagemRetorno("O tempo da sessão de votação expirou"));
		}

		for (Voto v: sessaoVotacao.get().getVotos()) {
			if (v.getCooperado().getId() == cooperado.get().getId()) {
				return ResponseEntity.status(406).body(new MensagemRetorno("O cooperado " + cooperado.get().getNome() + " já realizou o seu voto"));		
			}
		}

		return ResponseEntity.ok().body(votoRepository.save(voto));
	}
}
