package com.gestaopauta.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Realiza a criação dos contrutores padrão
@AllArgsConstructor

//Cria o construtor sem argumentos
@NoArgsConstructor

//Cria os métodos gets e sets, assim como o equals, toString, hashCode, etc...
@Data

//Define a classe como uma entidade do banco de dados
@Entity
public class Voto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(nullable = false, length = 1)
	private String opcao;
	
	@Column(nullable = false, name = "datahota_criacao")
	private LocalDateTime dataHoraCriacao;
	
	@OneToOne
	@JoinColumn(name = "id_cooperado")
	private Cooperado cooperado;
	
	@ManyToOne
	@JoinColumn(name = "id_sessao_votacao")
	private SessaoVotacao sessaoVotacao;
	
	@PrePersist
	public void prePersiste() {
		this.setDataHoraCriacao(LocalDateTime.now());
	}
}
