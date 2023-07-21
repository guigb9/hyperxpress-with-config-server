package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Suporte {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotBlank
	@Size(max = 200)
	private String nome;

	@NotBlank
	private String assunto;

	@NotBlank
	private String email;

	private Boolean ChamadoAberto = true;

	@NotBlank
	@Size(max = 500)
	private String mensagem;


}
