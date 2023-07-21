package com.bandtec.hyperxpress.hyperxpressproject.control.request;

import lombok.Getter;
import lombok.Setter;
import org.checkerframework.common.value.qual.BoolVal;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class AtualizacaoUsuarioRequest {
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

	@BoolVal(value = true)
	private Boolean emailConfirmado;
}
