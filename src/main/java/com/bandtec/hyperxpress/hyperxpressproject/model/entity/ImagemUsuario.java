package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ImagemUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(length = 1_000_000_999)
	private String conteudoImagem;

	@NotBlank
	private String nomeImagem;

	@ManyToOne
	private Usuario usuarioAssociado;
}
