package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.control.request.AvaliacaoUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.AvaliacoesUsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.AvaliacoesUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.AvaliacaoUsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/avaliacoes")
public class AvalicoesUsuarioController {

	private final AvaliacoesUsuarioService service;

	public AvalicoesUsuarioController(AvaliacoesUsuarioService avaliacoesUsuarioBusinessModel) {
		this.service = avaliacoesUsuarioBusinessModel;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AvaliacoesUsuario post(@Valid @RequestBody AvaliacaoUsuarioRequest request) {
		AvaliacoesUsuario avaliacao = service.setarInformacoes(request);
		return service.salvarAvaliacao(avaliacao);
	}

	@GetMapping(value = "/{idUsuario}", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Page<AvaliacaoUsuarioDTO> get(@Valid @PathVariable Long idUsuario, Pageable pageable) {
		return service.pegarAvaliacoesPorUsuario(idUsuario, pageable);
	}

	@DeleteMapping("/{idAvaliacao}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long idAvaliacao) {
		service.remocaoDeAvaliacao(idAvaliacao);
	}
}
