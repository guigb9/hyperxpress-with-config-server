package com.bandtec.hyperxpress.hyperxpressproject.control.request;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class CadastroUsuarioRequest {
	@NotBlank
	@Size(min = 5, max = 45)
	private String nome;

	@NotBlank
	@Size(min = 5, max = 45)
	private String avatar;

	@CPF
	private String cpf;

	@Email
	private String email;

	@NotBlank
	@Size(min = 8, max = 45)
	private String senha;

	@Past
	private LocalDate dataNasc;

	private Boolean emailConfirmado;

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

	public CadastroUsuarioRequest(String nome, String avatar, String cpf, String email, String senha, LocalDate dataNasc, Boolean emailConfirmado, String estadoUf, String cep, String bairro, String logradouro, String numero, String cidade, String complemento) {
		this.nome = nome;
		this.avatar = avatar;
		this.cpf = cpf;
		this.email = email;
		this.senha = senha;
		this.dataNasc = dataNasc;
		this.emailConfirmado = emailConfirmado;
		this.estadoUf = estadoUf;
		this.cep = cep;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.cidade = cidade;
		this.complemento = complemento;
	}
}
