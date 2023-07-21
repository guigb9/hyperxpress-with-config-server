package com.bandtec.hyperxpress.hyperxpressproject.model.repository;

import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmailAndSenha(String email, String senha);

	boolean existsByEmail(String email);

	Optional<Usuario> findByEmail(String email);
}
