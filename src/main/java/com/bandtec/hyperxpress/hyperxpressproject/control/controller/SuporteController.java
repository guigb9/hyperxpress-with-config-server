package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.control.service.impl.SuporteService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Suporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/report")
public class SuporteController {

	@Autowired
	private SuporteService service;

	@GetMapping
	public ResponseEntity getChamados(@PageableDefault(sort = {"nome"}) Pageable pageable) {
		if (!service.obterChamados(pageable).getContent().isEmpty()) {
			return status(200).body(service.obterChamados(pageable));
		}
		return status(204).build();
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ResponseEntity getChamadoId(@PathVariable Integer id) {
		if (service.obterChamadosPorId(id).isPresent()) {
			return status(200).body(service.obterChamadosPorId(id));
		}
		return status(204).build();
	}

	@PostMapping
	public ResponseEntity criarChamado(@RequestBody Suporte chamado) {
		service.salvarChamado(chamado);
		return status(201).build();
	}

	@PutMapping("/{id}")
	public ResponseEntity atenderChamado(@RequestParam int id) {
		if (service.obterChamadosPorId(id).isPresent()) {
			return status(200).body(service.atenderChamado(id));
		}
		return status(204).build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity deletarChamado(@PathVariable Integer id) {
		if (service.deletarChamado(id)) {
			return status(200).build();
		}
		return status(404).build();
	}
}
