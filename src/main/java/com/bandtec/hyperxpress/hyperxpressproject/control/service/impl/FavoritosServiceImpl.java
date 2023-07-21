package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.FavoritosService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.UsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.FavoritosUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.FavoritosUsuarioRepository;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoritosServiceImpl implements FavoritosService {

	private final FavoritosUsuarioRepository repositoryFavoritos;
	private final UsuarioService usuarioService;
	private final ProdutoService produtoService;

	public FavoritosServiceImpl(FavoritosUsuarioRepository repositoryFavoritos,
	                            UsuarioService usuarioService,
	                            ProdutoService produtoService) {
		this.repositoryFavoritos = repositoryFavoritos;
		this.usuarioService = usuarioService;
		this.produtoService = produtoService;
	}

	@Transactional
	public FavoritosUsuario salvarFavorito(Long idUsuario, Long idProduto) {
		Produto produto = produtoService.procurarProdutoPeloid(idProduto);
		Usuario usuario = usuarioService.procurarUsuarioPeloId(idUsuario);
		FavoritosUsuario favorito = new FavoritosUsuario();
		favorito.setProdutoFavorito(produto);
		favorito.setUsuarioFavoritou(usuario);
		return repositoryFavoritos.save(favorito);
	}

	public List<ProdutoGeralDTO> favoritosPorUsuario(Long idUsuario) {
		return repositoryFavoritos
				.findByUsuarioFavoritouIdIs(idUsuario)
				.stream()
				.map(FavoritosUsuario::getProdutoFavorito)
				.map(produtoService::toProdutoGeralDTO)
				.collect(Collectors.toList());
	}

    @Override
    public void removerFavorito(Long idUsuario, Long idProduto) {
		FavoritosUsuario favorite = repositoryFavoritos
				.findByUsuarioFavoritouIdIs(idUsuario)
				.stream().filter(favoritosUsuario -> favoritosUsuario.getProdutoFavorito()
				.getIdProduto().equals(idProduto)).findFirst()
				.orElseThrow(() -> new EmptyException("Favoritos usuário de id " + idUsuario + " com produto de id " + idProduto));
			repositoryFavoritos.delete(favorite);
	}
}
