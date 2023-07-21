package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoVendedorDTO {
	private Integer totalProdutosVendendo;

	private Long usuarioProdutoId;

	private String usuarioProdutoAvatar;

	private Integer totalPedidos;

	private Integer finalizados;

	private Double estrelas;

	private List<ProdutoDetalheVendedorDTO> produtos;
}
