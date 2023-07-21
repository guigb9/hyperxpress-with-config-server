package com.bandtec.hyperxpress.hyperxpressproject.model.repository;

import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusPedido;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusProduto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {
	List<Produto> findByCodigoPedidoId(Long idPedido);

	List<Produto> findByUsuarioProdutoIdAndCodigoPedidoStatus(Long idUsuario, StatusPedido statusPedido);

	List<Produto> findByStatusProdutoAndUsuarioProdutoId(StatusProduto statusProduto, Long idUsuario);

	List<Produto> findByUsuarioProdutoIdAndStatusProduto(Long idUsuario, StatusProduto statusProduto);


	Integer countByUsuarioProdutoIdAndStatusProduto(Long idUsuario, StatusProduto vendido);
}