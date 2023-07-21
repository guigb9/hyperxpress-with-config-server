package com.bandtec.hyperxpress.hyperxpressproject.model.repository;

import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemProduto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagemProdutoRepository extends JpaRepository<ImagemProduto, Long> {
    List<ImagemProduto> findByProdutoAssociadoIdProduto(Long idProduto);

    int countByProdutoAssociadoIdProduto(Long idProduto);
}
