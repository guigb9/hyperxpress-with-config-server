package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Endereco {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	@Size(min = 2, max = 2)
	private String estadoUf;

	@NotBlank
	@Size(min = 8, max = 10)
	private String cep;

	@NotBlank
	@Size(min = 3, max = 30)
	private String bairro;

	@NotBlank
	@Size(min = 3, max = 50)
	private String logradouro;

	@NotBlank
	@Size(min = 1, max = 5)
	private String numero;

	@NotBlank
	@Size(min = 3, max = 30)
	private String cidade;


	private String complemento;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario codigoUsuario;
}
