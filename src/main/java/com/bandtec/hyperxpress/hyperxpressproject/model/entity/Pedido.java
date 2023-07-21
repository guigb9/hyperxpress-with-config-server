package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusPedido;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@PositiveOrZero
	private Double valorTotal;

	@PositiveOrZero
	private Double valorFrete;

	@Enumerated(EnumType.STRING)
	private StatusPedido status;

	@NotNull
	private LocalDateTime dataPedido;

	@ManyToOne
	@JoinColumn(name = "entrega_id")
	private Entrega entrega;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "formapagamento_id")
	private FormaPagamento formaPagamento;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario codigoUsuario;
}
