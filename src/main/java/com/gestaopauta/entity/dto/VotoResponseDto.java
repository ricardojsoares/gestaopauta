package com.gestaopauta.entity.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class VotoResponseDto {

	private long id;
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime dataHoraVoto;
	private String mensagem;
}
