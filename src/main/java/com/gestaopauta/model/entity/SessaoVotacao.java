/**
 * Autor: Ricardo Soares
 * Data: 18/10/2021
 * **/

package com.gestaopauta.model.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "datahora_inicio")
	@JsonFormat(pattern = "dd/MM/yyyy hh:mm")
	private LocalDateTime datahoraInicio;
	
	@Column
	private int tempo;
	
	@OneToOne
	@JoinColumn(name="id_pauta")
	private Pauta pauta;

}
