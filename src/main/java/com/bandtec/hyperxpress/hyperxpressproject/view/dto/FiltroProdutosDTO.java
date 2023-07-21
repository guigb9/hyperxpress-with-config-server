package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import lombok.*;
import org.springframework.data.domain.Pageable;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FiltroProdutosDTO {

	private String nomeProduto;
	private String marca;
	private String tecido;
	private String genero;
	private Long subCategoria;
	private Integer categoria;
	private Double precoMinimo;
	private Double precoMax;
	private String tamanhoProduto;
	private Boolean trocavel;
	private Pageable pageable;
}
