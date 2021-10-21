package com.gestaopauta.entity.dto;

import lombok.Data;

@Data
public class VotoPostDto {

	private String opcao;
	private long idCooperado;
	private long idSessaoVotacao;
}
