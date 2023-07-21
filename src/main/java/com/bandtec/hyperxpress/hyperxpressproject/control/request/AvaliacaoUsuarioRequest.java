package com.bandtec.hyperxpress.hyperxpressproject.control.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class AvaliacaoUsuarioRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;

	@Length(min = 5, max = 140)
	public String comentario;

	@NotNull
	private Long usuarioAvaliadorId;

	@NotNull
	private Long usuarioAvaliadoId;

	@Positive
	@Max(5)
	private Double estrelas;
}
