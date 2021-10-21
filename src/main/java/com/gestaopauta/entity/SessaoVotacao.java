/**
 * Autor: Ricardo Soares
 * Data: 18/10/2021
 * **/

package com.gestaopauta.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
public class SessaoVotacao {

	public SessaoVotacao(int tempo, Pauta pauta) {
		this.tempo = tempo;
		this.pauta = pauta;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "datahora_inicio")
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime datahoraInicio;
	
	@Column
	private int tempo;
	
	@OneToOne
	@JoinColumn(name="id_pauta")
	private Pauta pauta;
	
	@OneToMany(mappedBy = "sessaoVotacao")
	@JsonIgnoreProperties("sessaoVotacao")
	private List<Voto> votos;

}
