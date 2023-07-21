package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.ProdutoException;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusProduto;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.ImagemRequest;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemProduto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.ImagemProdutoRepository;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.ImagemUsuarioRepository;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ImagensService {


	private final ImagemProdutoRepository repositoryImagemProduto;
	private final ImagemUsuarioRepository imagemUsuarioRepository;
	private final ProdutoRepository produtoRepository;


	public ImagemUsuario setarInfoUsuarioImagens(Usuario usuario, ImagemRequest imagem) {
		regraExclusao(usuario);
		ImagemUsuario imagemUsuario = new ImagemUsuario();
		imagemUsuario.setUsuarioAssociado(usuario);
		imagemUsuario.setNomeImagem("profile");
		imagemUsuario.setConteudoImagem(imagem.getImagem());
		return imagemUsuario;
	}

	public List<ImagemProduto> pegarImagensProdutoAssociado(long idProduto) {
		return repositoryImagemProduto.findByProdutoAssociadoIdProduto(idProduto);
	}

	public void salvarImagem(ImagemUsuario imagemProduto) {
		imagemUsuarioRepository.save(imagemProduto);
	}


	public Optional<ImagemUsuario> imagemUsuarioAssociado(long id) {
		return imagemUsuarioRepository.findByUsuarioAssociadoId(id);
	}

	public void setandoprodutoAssociadoComoNulo(ImagemProduto imagem) {
		imagem.setProdutoAssociado(null);
		repositoryImagemProduto.save(imagem);
	}

	@Transactional
	public void salvarImagemProduto(String imagem, Produto produto){
		if(repositoryImagemProduto.countByProdutoAssociadoIdProduto(produto.getIdProduto()) >= 4){
			throw new ProdutoException("O produto já possui 4 imagens associadas a ele.");
		}
		produto.setStatusProduto(StatusProduto.ATIVO);
		produtoRepository.save(produto);
		ImagemProduto imagemProduto = new ImagemProduto();
		imagemProduto.setImagem(imagem);
		imagemProduto.setProdutoAssociado(produto);
		imagemProduto.setDataInsercao(LocalDateTime.now());
		repositoryImagemProduto.save(imagemProduto);
	}

	@Transactional
	public void salvarImagemUsuario(String imagem, Usuario usuario){
		regraExclusao(usuario);
		ImagemRequest imagemRequest = new ImagemRequest();
		imagemRequest.setImagem(imagem);
		ImagemUsuario imgSaved = setarInfoUsuarioImagens(usuario, imagemRequest);
		imagemUsuarioRepository.save(imgSaved);
	}

	private void regraExclusao(Usuario usuario) {
		if(imagemUsuarioRepository.countByUsuarioAssociadoId(usuario.getId()) >= 1){
			List<ImagemUsuario> imagemUsuario = imagemUsuarioRepository.findByUsuarioAssociado_Id(usuario.getId());
			imagemUsuario.forEach(i -> i.setUsuarioAssociado(null));
			imagemUsuarioRepository.deleteAll(imagemUsuario);
		}
	}

	public void removerImagemPeloId(Long idImagem) {
		repositoryImagemProduto.findById(idImagem).ifPresent(i -> {
			setandoprodutoAssociadoComoNulo(i);
			repositoryImagemProduto.deleteById(idImagem);
		});
	}

}
