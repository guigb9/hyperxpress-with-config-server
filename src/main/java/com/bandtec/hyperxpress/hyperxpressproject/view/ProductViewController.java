package com.bandtec.hyperxpress.hyperxpressproject.view;

import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusPedido;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusProduto;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.AvaliacoesUsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.PedidoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.UsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.ProdutoRepository;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProductListView;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoDetalheVendedorDTO;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoVendedorDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/views/product-screen")
@RequiredArgsConstructor
public class ProductViewController {
	private final ModelMapper modelMapper;
	private final ProdutoService productService;
	private final ProdutoRepository produtoRepository;
	private final UsuarioService usuarioService;
	private final PedidoService pedidoService;
	private final AvaliacoesUsuarioService avaliacoesUsuarioService;

	@GetMapping
	public Page<ProdutoGeralDTO> get(Pageable pageable, Produto filter) {
		return productService.getProdutosAtivos(filter, pageable);
	}

	@GetMapping(value = "/product-list-view", produces = "application/json")
	public Page<ProductListView> getListView(Produto filter, Pageable pageable) {
		return getProductListView(filter, pageable);
	}

	@GetMapping(value = "/product-vendedor-view/{idUsuario}")
	@ResponseStatus(HttpStatus.OK)
	public ProdutoVendedorDTO getProductVendedorView(@PathVariable Long idUsuario) {
		return getProductVendedor(idUsuario);
	}

	public Page<ProductListView> getProductListView(Produto filter, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Produto> example = Example.of(filter, matcher);
		return produtoRepository.findAll(example, pageable).map(p -> {
			Double estrelas = avaliacoesUsuarioService
					.calcularMediaEstrelas(p.getUsuarioProduto());
			ProductListView productListView = toProductListView(p);
			productListView.setEstrelas(estrelas);
			return productListView;

		});
	}

	private ProductListView toProductListView(Produto produto) {
		return modelMapper.map(produto, ProductListView.class);
	}

	public ProdutoVendedorDTO getProductVendedor(Long idUsuario) {
		Usuario usuario = usuarioService.procurarUsuarioPeloId(idUsuario);
		List<Produto> produtos = produtoRepository.findByUsuarioProdutoIdAndStatusProduto(idUsuario, StatusProduto.ATIVO);
		ProdutoVendedorDTO produtoVendedorDTO = new ProdutoVendedorDTO();
		produtoVendedorDTO.setUsuarioProdutoId(usuario.getId());
		produtoVendedorDTO.setUsuarioProdutoAvatar(usuario.getAvatar());
		produtoVendedorDTO.setTotalPedidos(pedidoService.quantidadePedidosPendente(StatusPedido.PENDENTE, idUsuario));
		produtoVendedorDTO.setTotalProdutosVendendo(produtos.size());
		produtoVendedorDTO.setFinalizados(produtoRepository.countByUsuarioProdutoIdAndStatusProduto(idUsuario, StatusProduto.VENDIDO));
		produtoVendedorDTO.setEstrelas(avaliacoesUsuarioService.calcularMediaEstrelas(usuario));
		produtoVendedorDTO.setProdutos(produtos
				.stream().map(p -> {
					ProdutoDetalheVendedorDTO produtoDetalheVendedorDTO = modelMapper.map(p, ProdutoDetalheVendedorDTO.class);
					return produtoDetalheVendedorDTO;
				}).collect(Collectors.toList()));

		return produtoVendedorDTO;
	}
}