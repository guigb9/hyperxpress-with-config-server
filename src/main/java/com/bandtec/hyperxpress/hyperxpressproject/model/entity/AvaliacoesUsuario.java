package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class AvaliacoesUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(length = 150)
	@Length(min = 5, max = 140)
	private String comentario;

	@ManyToOne
	private Usuario usuarioAvaliador;

	@ManyToOne
	private Usuario usuarioAvaliado;

	@Positive
	@NotNull
	private Double estrelas;
}
