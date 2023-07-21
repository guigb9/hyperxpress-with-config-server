package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.ProdutoException;
import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.VendaException;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.NivelDestaque;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusPedido;
import com.bandtec.hyperxpress.hyperxpressproject.control.enums.StatusProduto;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemProduto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.ProdutoRepository;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ImagemDTO;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProdutoServiceImpl implements ProdutoService {

	private final ProdutoRepository produtoRepository;
	private final ModelMapper modelMapper;
	private final ImagensService imagensService;

	public Page<ProdutoGeralDTO> getProdutosAtivos(Produto filter, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);


		Example<Produto> example = Example.of(filter, matcher);
		Page<ProdutoGeralDTO> produtosPage = produtoRepository.findAll(example, pageable).map(this::toProdutoGeralDTO);
		if (produtosPage.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}

		return produtosPage;
	}


	public ProdutoGeralDTO toProdutoGeralDTO(Produto produto) {
		return modelMapper.map(produto, ProdutoGeralDTO.class);
	}

	@Transactional
	public void destacarProduto(Produto p, Integer nivelDestaque) {
		if (nivelDestaque.equals(1)) {
			p.setNivelDestaque(NivelDestaque.BASIC);
		} else if (nivelDestaque.equals(2)) {
			p.setNivelDestaque(NivelDestaque.INTERMEDIARY);
		} else if (nivelDestaque.equals(3)) {
			p.setNivelDestaque(NivelDestaque.ADVANCED);
		} else {
			throw new ProdutoException();
		}
		produtoRepository.save(p);
	}

	public List<Produto> produtosNoPedido(Long codigoPedido) {
		return produtoRepository.findByCodigoPedidoId(codigoPedido);
	}


	public void salvarProduto(Produto p) {
		produtoRepository.save(p);
	}

	public Produto setarStatusProduto(Produto produto) {
		produto.setStatusProduto(StatusProduto.ATIVO);
		return produto;
	}

	public Produto procurarProdutoPeloid(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new EmptyException("Produto"));
	}

	@Transactional
	public void removeProduto(Long idProduto) {
		Produto p = procurarProdutoPeloid(idProduto);
		if (Objects.nonNull(p.getCodigoPedido())) {
			log.error("nao é possível excluir um produto associado a um pedido");
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
		}
		produtoRepository.delete(p);
		log.info("removeu");
	}

	public ImagemProduto pegarImagem(Long id, int imagemEspecifica) {
		List<ImagemProduto> listaImagem = imagensService.pegarImagensProdutoAssociado(id);
		if (listaImagem.size() < imagemEspecifica) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return listaImagem.get(imagemEspecifica - 1);
	}

	@Transactional
	public Produto setarInfoParaPostagemProduto(Produto produto) {
		produto.setDataCriacao(LocalDateTime.now());
		produto.setNivelDestaque(NivelDestaque.BASIC);
		produto.setStatusProduto(StatusProduto.INATIVO);
		return produtoRepository.save(produto);
	}

	public List<ProdutoGeralDTO> pegarProdutosInativos(Long idUsuario) {
		return produtoRepository.
				findByStatusProdutoAndUsuarioProdutoId(StatusProduto.INATIVO, idUsuario)
				.stream()
				.map(this::toProdutoGeralDTO)
				.collect(Collectors.toList());
	}


	@Transactional
	public void saveToBase64(String image, Long idProduto){
		imagensService.salvarImagemProduto(image, produtoRepository.findById(idProduto).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)));
	}

	public List<ProdutoGeralDTO> pegarProdutosAtivosUsuario(Long idUsuario) {
		return produtoRepository.
				findByStatusProdutoAndUsuarioProdutoId(StatusProduto.ATIVO, idUsuario)
				.stream()
				.map(this::toProdutoGeralDTO)
				.collect(Collectors.toList());
	}

	public List<ProdutoGeralDTO> pegarProdutosPedidoEmAndamento(Long idUsuario) {
		return produtoRepository.
				findByUsuarioProdutoIdAndCodigoPedidoStatus(idUsuario, StatusPedido.PENDENTE)
				.stream()
				.map(this::toProdutoGeralDTO)
				.collect(Collectors.toList());
	}

	public List<ProdutoGeralDTO> pegarProdutosVendidos(Long idUsuario) {
		return produtoRepository.
				findByStatusProdutoAndUsuarioProdutoId(StatusProduto.VENDIDO, idUsuario)
				.stream()
				.map(this::toProdutoGeralDTO)
				.collect(Collectors.toList());
	}

	@Transactional
	public void venderProduto(Long idProduto) {
		Produto produto = produtoRepository.findById(idProduto).orElseThrow(VendaException::new);
		produto.setStatusProduto(StatusProduto.VENDIDO);
		salvarProduto(produto);
	}

	@Transactional
	public void tornarAtivo(Long idProduto) {
		Produto produto = produtoRepository.findById(idProduto).orElseThrow(VendaException::new);
		produto.setStatusProduto(StatusProduto.ATIVO);
		salvarProduto(produto);
	}

	@Transactional
	public void tornarInativo(Long idProduto) {
		Produto produto = produtoRepository.findById(idProduto).orElseThrow(VendaException::new);
		produto.setStatusProduto(StatusProduto.INATIVO);
		salvarProduto(produto);
	}

}

