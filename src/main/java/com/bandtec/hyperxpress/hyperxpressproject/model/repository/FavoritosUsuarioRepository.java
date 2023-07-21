package com.bandtec.hyperxpress.hyperxpressproject.model.repository;


import com.bandtec.hyperxpress.hyperxpressproject.model.entity.FavoritosUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoritosUsuarioRepository extends JpaRepository<FavoritosUsuario, Long> {
	List<FavoritosUsuario> findByUsuarioFavoritouIdIs(Long idUsuario);
}
