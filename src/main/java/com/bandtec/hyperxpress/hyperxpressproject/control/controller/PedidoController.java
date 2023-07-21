package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusPedido;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusProduto;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.AtualizacaoStatusPedidoRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.PedidoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Pedido;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.PedidoDTO;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidoController {

	private final ProdutoService produtoService;
	private final PedidoService pedidoService;

	@GetMapping(value = "/usuario/{idUsuario}", produces = "application/json")
	public List<PedidoDTO> getPedidosUsuario(@PathVariable Long idUsuario) {
		return pedidoService.pedidoPorUsuario(idUsuario);
	}

	@GetMapping(value = "/{codigoPedido}", produces = "application/json")
	public PedidoDTO pegarPedido(@PathVariable Long codigoPedido) {
		return pedidoService.toPedidoDTO(pedidoService.pegarPedidoPeloId(codigoPedido));
	}

	@PatchMapping("/pendente/{idPedido}")
	public void alterarStatusPedido(@PathVariable Long idPedido, @RequestBody AtualizacaoStatusPedidoRequest request) {
		pedidoService.alterarStatusPedido(idPedido, StatusPedido.valueOf(request.getStatus()));
	}

	@GetMapping(value = "/produtos/{codigoPedido}", produces = "application/json")
	public List<ProdutoGeralDTO> getProdutosPedido(@PathVariable Long codigoPedido) {
		List<Produto> produtosNoPedido = produtoService.produtosNoPedido(codigoPedido);

		if (produtosNoPedido.isEmpty()) {
			log.info("Não há produtos no pedido de código {}", codigoPedido);
			throw new ResponseStatusException(NO_CONTENT);
		}
		return produtosNoPedido
				.stream()
				.map(p -> {
					p.setStatusProduto(StatusProduto.INATIVO);
					return produtoService.toProdutoGeralDTO(p);
				})
				.collect(Collectors.toList());
	}

	@GetMapping(produces = "application/json")
	public Page<Pedido> getPedidos(@PageableDefault() Pageable pageable) {
		Page<Pedido> pedidos = pedidoService.todosPedidos(pageable);
		if (pedidos.getContent().isEmpty()) {
			throw new ResponseStatusException(NO_CONTENT);
		}
		return pedidos;
	}

	@PostMapping("/{idUsuario}")
	@ResponseStatus(CREATED)
	public PedidoDTO postPedido(@PathVariable Long idUsuario) {
		return pedidoService.criarPedido(idUsuario);
	}
}