package com.bandtec.hyperxpress.hyperxpressproject.view.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioLoginDTO {
	private Long id;
	private String nome;
	private String avatar;
	private LocalDate dataNasc;
	private String email;
	private String senha;
}
