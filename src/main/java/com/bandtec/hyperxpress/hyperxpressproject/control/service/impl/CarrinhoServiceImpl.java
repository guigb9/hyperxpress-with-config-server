package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.control.service.CarrinhoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Carrinho;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.CarrinhoRepository;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarrinhoServiceImpl implements CarrinhoService {

	private final CarrinhoRepository carrinhoRepository;
	private final ProdutoService produtoServiceImpl;

	public CarrinhoServiceImpl(CarrinhoRepository carrinhoRepository, ProdutoService produtoServiceImpl) {
		this.carrinhoRepository = carrinhoRepository;
		this.produtoServiceImpl = produtoServiceImpl;
	}

	public boolean verificarSeEstaNoCarrinho(Long idProduto, Long idUsuario) {
		return carrinhoRepository.findByProdutoAssociadoIdProdutoAndUsuarioAdicionouId(idProduto, idUsuario).isEmpty();
	}

	@Transactional
	public void adicionarAoCarrinho(Produto produto, Usuario usuario) {
		Carrinho c = new Carrinho();
		c.setProdutoAssociado(produto);
		c.setUsuarioAdicionou(usuario);
		carrinhoRepository.save(c);
	}

	public void salvarCarrinho(Carrinho c) {
		carrinhoRepository.save(c);
	}

	public void excluirCarrinho(Long idCarrinho) {
		carrinhoRepository.deleteById(idCarrinho);
	}


	public List<ProdutoGeralDTO> pegarProdutosCarrinho(List<Carrinho> produtos) {
		return produtos
				.stream()
				.map(p -> produtoServiceImpl.toProdutoGeralDTO(p.getProdutoAssociado()))
				.collect(Collectors.toList());
	}

	public List<Carrinho> produtosCarrinhoUsuarioEspecifico(Long idUsuario) {
		return carrinhoRepository.findByUsuarioAdicionouId(idUsuario);
	}

	public Carrinho pegarProduto(Long idProduto, Long idUsuario) {
		return carrinhoRepository.findByProdutoAssociadoIdProdutoAndUsuarioAdicionouId(idProduto, idUsuario).get(0);
	}

	public void desvincularProdutoDoCarrinho(Carrinho produto) {
		produto.setProdutoAssociado(null);
		produto.setUsuarioAdicionou(null);
		carrinhoRepository.save(produto);
	}

	public void exclusaoDoProduto(Carrinho produto) {
		desvincularProdutoDoCarrinho(produto);
		carrinhoRepository.deleteById(produto.getId());
	}
}
