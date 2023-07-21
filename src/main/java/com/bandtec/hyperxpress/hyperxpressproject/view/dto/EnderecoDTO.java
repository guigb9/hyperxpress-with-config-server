package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {
	private String estadoUf;

	private String cep;

	private String bairro;

	private String logradouro;

	private String numero;

	private String cidade;

	private String complemento;

	private String codigoUsuarioNome;

	private String codigoUsuarioCpf;
}
