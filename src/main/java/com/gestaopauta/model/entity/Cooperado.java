package com.gestaopauta.model.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
public class Cooperado {
	
	@Id
	@Column(length = 11)
	private String cpf;
	
	@Column(nullable = false, length = 150)
	private String nome;
	
	@Column
	private EnumStatusCooperado status;
	
	@Column(name = "data_cadastro")
	private LocalDate dataCadastro;
}
