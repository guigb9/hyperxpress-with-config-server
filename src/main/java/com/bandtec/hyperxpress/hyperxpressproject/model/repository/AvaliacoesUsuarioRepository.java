package com.bandtec.hyperxpress.hyperxpressproject.model.repository;

import com.bandtec.hyperxpress.hyperxpressproject.model.entity.AvaliacoesUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface AvaliacoesUsuarioRepository extends PagingAndSortingRepository<AvaliacoesUsuario, Long> {


	Page<AvaliacoesUsuario> findByUsuarioAvaliadoId(Long idUsuario, Pageable pageable);

	@Query(value = "select AVG(Estrelas) from avaliacoes_usuario where usuario_avaliado_id = ?1", nativeQuery = true)
	Double calcMediaEstrelas(Usuario usuario);
}
