package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Entrega;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {
	private Long id;

	private String nomeUsuarioPedido;

	private String nomeAvatarPedido;

	private String emailUsuarioPedido;

	private String cpfUsuarioPedido;

	private String formaPagamentoNomeFp;

	private String status;

	private Double valorTotal;

	private Double valorFrete;

}
