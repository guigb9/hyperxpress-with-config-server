package com.bandtec.hyperxpress.hyperxpressproject.model.repository;

import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusPedido;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
	List<Pedido> findByCodigoUsuarioId(Long idUsuario);

	Integer countByStatusAndCodigoUsuarioId(StatusPedido statusPedido, Long codigoUsuario);
}
