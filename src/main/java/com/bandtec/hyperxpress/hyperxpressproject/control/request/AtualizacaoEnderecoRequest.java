package com.bandtec.hyperxpress.hyperxpressproject.control.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AtualizacaoEnderecoRequest {
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
	@Size(min = 3, max = 80)
	private String logradouro;

	@NotBlank
	@Size(min = 1, max = 5)
	private String numero;

	@NotBlank
	@Size(min = 3, max = 30)
	private String cidade;

	private String complemento;
}
