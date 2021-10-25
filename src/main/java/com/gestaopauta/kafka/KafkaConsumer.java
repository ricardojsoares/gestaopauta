package com.gestaopauta.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumer {

	@KafkaListener(topics = "voto-registrado", groupId = "group_id")
	public void consume(String message) {
		System.out.println("Mensagem: " + message);
	}
}
