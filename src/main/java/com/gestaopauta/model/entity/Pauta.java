/**
 * Autor: Ricardo Soares
 * Data: 16/10/2021
 * **/

package com.gestaopauta.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gestaopauta.model.resources.EnumStatusPauta;

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
public class Pauta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(length = 100)
	private String titulo;
	
	@Column(length = 500)
	private String descricao;
	
	@Column
	private EnumStatusPauta status;
	
	@Column(name = "data_cadastro")
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate dataCadastro;
	
	@PrePersist
	public void PrePersist() {
		setDataCadastro(LocalDate.now());
		setStatus(EnumStatusPauta.NONE);
	}
}
