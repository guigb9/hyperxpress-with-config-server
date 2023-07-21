package com.bandtec.hyperxpress.hyperxpressproject.model.repository;

import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    List<Carrinho> findByUsuarioAdicionouId(Long id);
    List<Carrinho> findByProdutoAssociadoIdProdutoAndUsuarioAdicionouId(Long id, Long idUsuario);
}
