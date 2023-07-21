package com.bandtec.hyperxpress.hyperxpressproject.model.repository;

import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface ImagemUsuarioRepository extends JpaRepository<ImagemUsuario, Long> {

	Optional<ImagemUsuario> findByUsuarioAssociadoId(Long idProduto);

	List<ImagemUsuario> findByUsuarioAssociado_Id(Long idProduto);


	int countByUsuarioAssociadoId(Long idUsuario);
}
