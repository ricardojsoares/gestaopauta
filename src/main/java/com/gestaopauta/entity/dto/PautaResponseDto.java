package com.gestaopauta.entity.dto;

import com.gestaopauta.resources.EnumStatusPauta;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PautaResponseDto {

	private long idPauta;
	private long totalVotos;
	private long totalSim;
	private long totalNao;
	private EnumStatusPauta status;
}
