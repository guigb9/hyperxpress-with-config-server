package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.CarrinhoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.UsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Carrinho;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/carrinhos")
@RequiredArgsConstructor
public class CarrinhoController {

	private final CarrinhoService carrinhoService;
	private final UsuarioService serviceUsuario;
	private final ProdutoService serviceProduto;

	@PostMapping("/{idProduto}/{idUsuario}")
	@ResponseStatus(HttpStatus.CREATED)
	public void adicionarCarrinho(@PathVariable Long idProduto,
	                              @PathVariable Long idUsuario) {
		Usuario usuario = serviceUsuario.procurarUsuarioPeloId(idUsuario);
		Produto produto = serviceProduto.procurarProdutoPeloid(idProduto);
		if (usuario.getId().equals(produto.getUsuarioProduto().getId())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Você não pode adicionar o próprio produto ao carrinho");
		} else if (carrinhoService.verificarSeEstaNoCarrinho(idProduto, idUsuario)) {
			carrinhoService.adicionarAoCarrinho(produto, usuario);
		}
	}

	@GetMapping(value = "/{idUsuario}", produces = "application/json")
	public List<ProdutoGeralDTO> getProdutosPorUsuario(@PathVariable Long idUsuario) {
		List<Carrinho> produtos = carrinhoService.produtosCarrinhoUsuarioEspecifico(idUsuario);
		if (produtos.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return carrinhoService.pegarProdutosCarrinho(produtos);
	}

	@DeleteMapping("/{idProduto}/{idUsuario}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteProdutoCarrinho(@PathVariable Long idProduto, @PathVariable Long idUsuario) {
		carrinhoService.exclusaoDoProduto(carrinhoService.pegarProduto(idProduto, idUsuario));
	}
}
