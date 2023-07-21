package com.bandtec.hyperxpress.hyperxpressproject.control.service;


import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Carrinho;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;

import java.util.List;

public interface CarrinhoService {
	boolean verificarSeEstaNoCarrinho(Long idProduto, Long idUsuario);

	void adicionarAoCarrinho(Produto produto, Usuario usuario);

	List<Carrinho> produtosCarrinhoUsuarioEspecifico(Long idUsuario);

	Carrinho pegarProduto(Long idProduto, Long idUsuario);

	void exclusaoDoProduto(Carrinho carrinho);

	List<ProdutoGeralDTO> pegarProdutosCarrinho(List<Carrinho> produtos);

	void salvarCarrinho(Carrinho c);

	void excluirCarrinho(Long idCarrinho);


}
