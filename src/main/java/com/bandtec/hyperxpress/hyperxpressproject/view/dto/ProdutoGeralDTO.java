package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoGeralDTO {
	private Long idProduto;

	private String nomeProduto;

	private String usuarioProdutoAvatar;

	private Double precoProduto;

	private String tamanhoProduto;

	private String usuarioProdutoNome;

	private String usuarioProdutoEmail;

	private Boolean trocavel;

	private String marca;

	private String descricaoProduto;

	private String usuarioProdutoCpf;

	private Long codigoPedidoId;

	private String telefone;

	private String status;


}
