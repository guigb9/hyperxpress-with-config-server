package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ImagemProduto {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(length = 1_000_000_999)
	private String imagem;

	@ManyToOne
	private Produto produtoAssociado;

	@CreatedDate
	private LocalDateTime dataInsercao;
}
