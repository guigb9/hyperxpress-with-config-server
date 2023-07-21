package com.bandtec.hyperxpress.hyperxpressproject.control.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/emails")
@RequiredArgsConstructor
public class EmailController {
	private final JavaMailSender mailSender;

	@GetMapping("/enviar")
	@ResponseStatus(HttpStatus.OK)
	public void enviar(@RequestParam String email) {
		Integer codigo = ThreadLocalRandom.current().nextInt(100000, 999999);
		SimpleMailMessage message = new SimpleMailMessage();
		message.setText(String.format("Olá, Bem vindo a HyperXpress \n Ficamos felizes com sua chegada, e esperamos " +
				"lhe ajudar no que for preciso!\n Seu código de verificação: %d \n Qualquer duvida, é só entrar em contato conosco, tmj! <3", codigo));
		message.setSubject("HyperXpress");
		message.setTo(email);
		message.setFrom("help@hyperxpress.com.br");
		mailSender.send(message);
	}
}
