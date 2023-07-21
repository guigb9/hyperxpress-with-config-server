package com.bandtec.hyperxpress.hyperxpressproject.control.service;

import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusPedido;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Pedido;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.PedidoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PedidoService {
	List<PedidoDTO> pedidoPorUsuario(Long idUsuario);

	Pedido pegarPedidoPeloId(Long codigoPedido);

	PedidoDTO toPedidoDTO(Pedido pedido);

	void alterarStatusPedido(Long idPedido, StatusPedido statusPedido);

	Page<Pedido> todosPedidos(Pageable pageable);

	PedidoDTO criarPedido(Long idUsuario);

	Integer quantidadePedidosPendente(StatusPedido statusPedido, Long idUsuario);
}
