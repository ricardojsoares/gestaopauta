package com.gestaopauta.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gestaopauta.model.entity.Cooperado;

@RestController
public class CooperadoController {

	@GetMapping("/coop")
	public Cooperado getCooperado() {
		Cooperado coop = new Cooperado();
		coop.setCpf("11001100");
		coop.setNome("Ricardo Soares");
		return coop;
	}
}
