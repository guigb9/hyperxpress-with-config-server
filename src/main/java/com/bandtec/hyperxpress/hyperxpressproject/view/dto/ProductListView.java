package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusProduto;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductListView {
	private Long idProduto;

	private String nomeProduto;

	private Double precoProduto;

	private String tamanhoProduto;

	private Boolean trocavel;

	private String marca;

	private String tecido;

	private String descricaoProduto;

	private String tamanho;

	private Long usuarioProdutoId;

	private String usuarioProdutoCpf;

	private String usuarioProdutoEmail;

	private String usuarioProdutoAvatar;

	private Double estrelas;

	private Long codigoPedidoId;

	private StatusProduto statusProduto;

	private String telefone;
}
