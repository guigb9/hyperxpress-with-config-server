package com.bandtec.hyperxpress.hyperxpressproject.control.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.*;

@Getter
@Setter
public class PostagemProdutoRequest {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProduto;

	@NotBlank
	@Size(min = 3, max = 100)
	private String nomeProduto;

	@NotBlank
	@Size(min = 20, max = 255)
	private String descricaoProduto;

	@Positive
	@NotNull
	@Digits(integer = 9, fraction = 2)
	private Double precoProduto;

	@NotBlank
	@Length(max = 5)
	private String tamanhoProduto;

	@NotBlank
	private String genero;

	@NotNull
	private Boolean trocavel;

	@NotNull
	private Long subCategoriaId;


	@NotNull
	private Long codigoUsuarioProdId;

	@NotBlank
	@Length(min = 1, max = 50)
	private String tecido;

	@NotBlank
	@Length(min = 1, max = 35)
	private String marca;

	@NotBlank
	@Length(max = 16)
	private String telefone;
}
