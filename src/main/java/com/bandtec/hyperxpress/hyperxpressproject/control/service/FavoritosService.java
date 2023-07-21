package com.bandtec.hyperxpress.hyperxpressproject.control.service;

import com.bandtec.hyperxpress.hyperxpressproject.model.entity.FavoritosUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;

import java.util.List;

public interface FavoritosService {
	FavoritosUsuario salvarFavorito(Long idUsuario, Long idProduto);

	List<ProdutoGeralDTO> favoritosPorUsuario(Long idUsuario);
	void removerFavorito(Long idUsuario, Long idProduto);
}
