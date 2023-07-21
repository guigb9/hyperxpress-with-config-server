package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(min = 5, max = 45)
	private String nome;

	@NotBlank
	@Size(min = 5, max = 45)
	private String avatar;

	@CPF
	private String cpf;

	@Email
	@Column(length = 45)
	private String email;

	@NotBlank
	@Size(min = 8, max = 15)
	private String senha;

	@Past
	private LocalDate dataNasc;

	private String googleId;

	@NotNull
	private Boolean emailConfirmado;
}
