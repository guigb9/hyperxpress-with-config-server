package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.ProdutoException;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.ImagemRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemProduto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.ImagemProdutoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImagensProdutoService {

	private final ImagemProdutoRepository imagemProdutoRepository;
	private final ProdutoService produtoServiceImpl;


	public void salvarImagens(ImagemProduto imagemProduto) {
		imagemProdutoRepository.save(imagemProduto);
	}

	@Transactional
	public void anexarImagensAoProduto(Long idProduto, ImagemRequest imagem){
		produtoServiceImpl.procurarProdutoPeloid(idProduto);
		if (quantidadeImagensProduto(idProduto) >= 4) {
			log.error("O limite de imagens do produto foi ultrapassado");
			throw new ProdutoException("O limite de imagens do produto ja foi ultrapassado (4).");
		}
		salvarImagensAoProduto(imagem, idProduto);
	}

	public Integer quantidadeImagensProduto(Long idProduto) {
		return imagemProdutoRepository.countByProdutoAssociadoIdProduto(idProduto);
	}

	public void salvarImagemProduto(Produto produto, ImagemRequest imagem) {
		ImagemProduto imagemProduto = new ImagemProduto();
		imagemProduto.setProdutoAssociado(produto);
		imagemProduto.setImagem(imagem.getImagem());
		imagemProduto.setDataInsercao(LocalDateTime.now());
		salvarImagens(imagemProduto);
	}

	@Transactional
	public void salvarImagensAoProduto(ImagemRequest imagem, Long id) {
		Produto produto = produtoServiceImpl.procurarProdutoPeloid(id);
		produtoServiceImpl.salvarProduto(produtoServiceImpl.setarStatusProduto(produto));
		salvarImagemProduto(produto, imagem);
	}
}
