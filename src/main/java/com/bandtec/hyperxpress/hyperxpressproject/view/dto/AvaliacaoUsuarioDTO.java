package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoUsuarioDTO {
	private Long id;

	private Double estrelas;

	private String comentario;

	private Long idUsuarioAvaliador;

	private Long idUsuarioAvaliado;

	private String nomeUsuarioAvaliador;

	private String nomeUsuarioAvaliado;
}
