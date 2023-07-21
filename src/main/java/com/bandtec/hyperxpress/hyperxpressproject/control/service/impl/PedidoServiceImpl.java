package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;


import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.CarrinhoException;
import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusPedido;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.*;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Carrinho;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Pedido;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.PedidoRepository;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.PedidoDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class PedidoServiceImpl implements PedidoService {
	private final PedidoRepository pedidoRepository;
	private final ProdutoService produtoService;
	private final CarrinhoService carrinhoService;
	private final UsuarioService usuarioService;
	private final FormaPagamentoService formaPagamentoService;
	private final ModelMapper modelMapper;


	public List<PedidoDTO> pedidoPorUsuario(Long idUsuario) {
		List<PedidoDTO> pedidos = pedidoRepository.findByCodigoUsuarioId(idUsuario).stream().map(this::toPedidoDTO).collect(Collectors.toList());
		if (pedidos.isEmpty()) {
			throw new EmptyException("Pedidos para usuário de id " + idUsuario);
		}
		return pedidos;
	}

	public Pedido pegarPedidoPeloId(Long idPedido) {
		return pedidoRepository.findById(idPedido).orElseThrow(() -> new EmptyException("Pedido"));
	}

	public PedidoDTO toPedidoDTO(Pedido pedido) {
		return modelMapper.map(pedido, PedidoDTO.class);
	}

	@Transactional
	public void alterarStatusPedido(Long idPedido, StatusPedido statusPedido) {
		Pedido pedido = pegarPedidoPeloId(idPedido);
		pedido.setStatus(statusPedido);
		pedidoRepository.save(pedido);
	}

	public Page<Pedido> todosPedidos(Pageable pageable) {
		return pedidoRepository.findAll(pageable);
	}

	@Transactional
	public PedidoDTO criarPedido(Long idUsuario) {
		Usuario usuario = usuarioService.procurarUsuarioPeloId(idUsuario);
		List<Carrinho> carrinhos = verificarSeCarrinhoPossuiProdutos(idUsuario);
		Pedido pedido = new Pedido();
		pedido.setDataPedido(LocalDateTime.now());
		setandoInformacoesPedido(usuario, pedido, carrinhos);
		pedidoRepository.save(pedido);
		return toPedidoDTO(pedido);
	}

	public Integer quantidadePedidosPendente(StatusPedido statusPedido, Long idUsuario) {
		return pedidoRepository.countByStatusAndCodigoUsuarioId(statusPedido, idUsuario);
	}

	private List<Carrinho> verificarSeCarrinhoPossuiProdutos(Long idUsuario) {
		List<Carrinho> carrinhos = carrinhoService.produtosCarrinhoUsuarioEspecifico(idUsuario);
		if (carrinhos.isEmpty()) {
			throw new CarrinhoException();
		}
		return carrinhos;
	}

	public void setandoInformacoesPedido(Usuario usuario, Pedido pedido, List<Carrinho> carrinhos) {
		pedido.setStatus(StatusPedido.PENDENTE);
		pedido.setFormaPagamento(formaPagamentoService.pegarFormaPagamento(1));
		pedido.setCodigoUsuario(usuario);
		pedido.setValorTotal(passarProdutoDoCarrinhoParaPedido(pedido, carrinhos));
	}

	public Double passarProdutoDoCarrinhoParaPedido(Pedido pedido, List<Carrinho> carrinhos) {
		AtomicReference<Double> valorTotal = new AtomicReference<>(0.0);
		carrinhos.forEach(c -> {
			Produto produto = c.getProdutoAssociado();
			produto.setCodigoPedido(pedido);
			valorTotal.updateAndGet(v -> v + produto.getPrecoProduto());
			c.setProdutoAssociado(null);
			c.setUsuarioAdicionou(null);
			carrinhoService.salvarCarrinho(c);
			carrinhoService.excluirCarrinho(c.getId());
			produtoService.salvarProduto(produto);
		});
		pedido.setValorTotal(valorTotal.get());
		return valorTotal.get();
	}
}
