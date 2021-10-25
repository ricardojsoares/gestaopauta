package com.gestaopauta.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.gestaopauta.entity.Retorno;
import com.gestaopauta.resources.EnumStatusCooperado;

@Service
public class ValidaCpfServiceImpl implements ValidaCpfService {

	@Override
	public EnumStatusCooperado validaCpf(String Cpf) {
		try {
			RestTemplate restTemplate = new RestTemplate();

			UriComponents uri = UriComponentsBuilder.newInstance().scheme("http")
					.host("localhost:8080/rest/collection/").path("v1/validaCpf/" + Cpf).build();

			ResponseEntity<Retorno> retorno = restTemplate.getForEntity(uri.toUriString(), Retorno.class);

			return retorno.getBody().getStatus();
		} catch (Exception e) {
			return EnumStatusCooperado.UNABLE_TO_VOTE;
		}
	}
}
