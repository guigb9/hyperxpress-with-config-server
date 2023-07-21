package com.bandtec.hyperxpress.hyperxpressproject.model.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class FavoritosUsuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idFavorito;

	@ManyToOne
	private Produto produtoFavorito;

	@ManyToOne
	private Usuario usuarioFavoritou;
}
