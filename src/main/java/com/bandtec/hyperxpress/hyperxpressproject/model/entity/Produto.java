package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import com.bandtec.hyperxpress.hyperxpressproject.control.enums.NivelDestaque;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusProduto;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Produto {
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
	private Double precoProduto;

	@NotBlank
	private String tamanhoProduto;

	@Enumerated(EnumType.STRING)
	private StatusProduto statusProduto;

	@NotBlank
	private String genero;

	@NotBlank
	private String telefone;

	@NotNull
	private Boolean trocavel;

	@Enumerated(EnumType.ORDINAL)
	private NivelDestaque nivelDestaque;

	@ManyToOne
	@JoinColumn(name = "subcategoria_id")
	private SubCategoria subCategoria;

	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuarioProduto;

	@ManyToOne
	@JoinColumn(name = "pedido_id")
	private Pedido codigoPedido;

	@NotBlank
	@Length(min = 1, max = 50)
	private String tecido;

	@NotBlank
	@Length(min = 1, max = 35)
	private String marca;

	@NotNull
	private LocalDateTime dataCriacao;
}
