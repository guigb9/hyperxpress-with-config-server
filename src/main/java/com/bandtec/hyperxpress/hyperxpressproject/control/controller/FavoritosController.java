package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.FavoritosService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.FavoritosUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
@RequiredArgsConstructor
public class FavoritosController {

	private final FavoritosService service;

	@PostMapping("/{idUsuario}/{idProduto}")
	@ResponseStatus(HttpStatus.CREATED)
	public FavoritosUsuario postFavorito(@PathVariable Long idUsuario, @PathVariable Long idProduto) {
		return service.salvarFavorito(idUsuario, idProduto);
	}

	@GetMapping(value = "/{idUsuario}", produces = "application/json")
	public List<ProdutoGeralDTO> getFavoritosUsuario(@PathVariable Long idUsuario) {
		List<ProdutoGeralDTO> favoritos = service.favoritosPorUsuario(idUsuario);
		if (favoritos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return favoritos;
	}

	@DeleteMapping("/{idUsuario}/{idProduto}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removerFavorito(@PathVariable Long idUsuario, @PathVariable Long idProduto){
		 service.removerFavorito(idUsuario,idProduto);
	}

}
