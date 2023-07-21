package com.bandtec.hyperxpress.hyperxpressproject.control.service;

import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemProduto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProdutoService {
	Page<ProdutoGeralDTO> getProdutosAtivos(Produto produto, Pageable pageable);

	Produto procurarProdutoPeloid(Long id);

	ProdutoGeralDTO toProdutoGeralDTO(Produto procurarProdutoPeloid);

	Produto setarInfoParaPostagemProduto(Produto produto);

	void destacarProduto(Produto produto, Integer nivelDestaque);

	void removeProduto(Long idProduto);

	ImagemProduto pegarImagem(Long id, int imagemEspecifica);

	void salvarProduto(Produto produto);

	List<ProdutoGeralDTO> pegarProdutosInativos(Long idUsuario);

	List<ProdutoGeralDTO> pegarProdutosAtivosUsuario(Long idUsuario);

	List<ProdutoGeralDTO> pegarProdutosPedidoEmAndamento(Long idUsuario);

	List<ProdutoGeralDTO> pegarProdutosVendidos(Long idUsuario);

	void venderProduto(Long idProduto);

	void tornarAtivo(Long idProduto);

	void tornarInativo(Long idProduto);

	List<Produto> produtosNoPedido(Long codigoPedido);

	Produto setarStatusProduto(Produto produto);

	void saveToBase64(String imageBase64, Long idProduto);
}
