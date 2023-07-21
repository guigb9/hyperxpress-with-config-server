package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendas")
@RequiredArgsConstructor
public class VendaController {
	private final ProdutoService produtoServiceImpl;

	@GetMapping(value = "/inativos/{idUsuario}", produces = "application/json")
	public List<ProdutoGeralDTO> getProdutosInativos(@PathVariable Long idUsuario) {
		return produtoServiceImpl.pegarProdutosInativos(idUsuario);
	}

	@GetMapping(value = "/ativos/{idUsuario}", produces = "application/json")
	public List<ProdutoGeralDTO> getProdutosVendedor(@PathVariable Long idUsuario) {
		return produtoServiceImpl.pegarProdutosAtivosUsuario(idUsuario);
	}

	@GetMapping(value = "/vendidos/{idUsuario}", produces = "application/json")
	public List<ProdutoGeralDTO> getProdutosVendedorConcluidos(@PathVariable Long idUsuario) {
		return produtoServiceImpl.pegarProdutosVendidos(idUsuario);
	}

	@PutMapping("/vender/{idProduto}")
	public void venderProduto(@PathVariable Long idProduto) {
		produtoServiceImpl.venderProduto(idProduto);
	}

	@PutMapping("/tornarAtivo/{idProduto}")
	public void tornarProdutoAtivo(@PathVariable Long idProduto) {
		produtoServiceImpl.tornarAtivo(idProduto);
	}

	@PutMapping("/tornarInativo/{idProduto}")
	public void tornarProdutoInativo(@PathVariable Long idProduto) {
		produtoServiceImpl.tornarInativo(idProduto);
	}
}
