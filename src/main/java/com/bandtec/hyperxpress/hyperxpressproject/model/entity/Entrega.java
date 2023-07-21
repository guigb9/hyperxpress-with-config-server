package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Entrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	@Size(min = 5, max = 45)
	private String statusEntrega;

	@FutureOrPresent
	private LocalDate dataEntrega;

	@NotBlank
	@Size(min = 5, max = 45)
	private String observacaoEntrega;

	@FutureOrPresent
	private LocalDate dataPrevisaoEntrega;

	@NotBlank
	@Size(min = 5, max = 45)
	private String codigoViagem;
}
