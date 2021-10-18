package com.gestaopauta.model.resources;

public class Validacoes {

	public static Boolean validaString(String inputString) {
		return !(inputString == null || inputString.isEmpty());
	}
}
