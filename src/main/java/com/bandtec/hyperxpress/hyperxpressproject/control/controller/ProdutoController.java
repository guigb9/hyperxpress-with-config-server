package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.control.request.ImagemRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.PostagemProdutoRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.ProdutoDestaque;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.ProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.impl.ImagensProdutoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.impl.ImagensService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemProduto;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Produto;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ImagemDTO;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.ProdutoGeralDTO;
import lombok.SneakyThrows;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.io.IOException;
import java.util.Base64;

import static com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.Messages.EMPTY_LIST;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;


@RestController
@RequestMapping("/produtos")
public class ProdutoController {

	private final ProdutoService produtoService;
	private final ImagensService imagensService;
	private final ImagensProdutoService imagensProdutoService;
	private final ModelMapper modelMapper;

	public ProdutoController(ProdutoService produtoService, ImagensService imagensService, ImagensProdutoService imagensProdutoService1, ModelMapper modelMapper) {
		this.produtoService = produtoService;
		this.imagensService = imagensService;
		this.imagensProdutoService = imagensProdutoService1;
		this.modelMapper = modelMapper;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoController.class);


	@GetMapping(produces = "application/json")
	public Page<ProdutoGeralDTO> getProdutos(Produto filter, @PageableDefault(sort = {"dataCriacao"},direction = Sort.Direction.DESC) Pageable pageable) {
		Page<ProdutoGeralDTO> produtos = produtoService.getProdutosAtivos(filter, pageable);
		if (produtos.isEmpty()) {
			LOGGER.error(EMPTY_LIST, "produtos");
			throw new ResponseStatusException(NO_CONTENT);
		}
		return produtos;
	}

	@GetMapping(value = "/{id}", produces = "application/json")
	public ProdutoGeralDTO getById(@PathVariable Long id) {
		return produtoService.toProdutoGeralDTO(produtoService.procurarProdutoPeloid(id));
	}


	@SneakyThrows
	@PutMapping("/imagem/{idProduto}")
	public void anexarImagensAoProduto(@RequestBody @Valid ImagemRequest imagem,
									   @PathVariable Long idProduto) {
		imagensProdutoService.anexarImagensAoProduto(idProduto, imagem);
	}


	@PostMapping
	@ResponseStatus(CREATED)
	public Produto postProduto(@Valid @RequestBody PostagemProdutoRequest request) {
		Produto produto = toProduto(request);
		return produtoService.setarInfoParaPostagemProduto(produto);
	}

	private Produto toProduto(PostagemProdutoRequest request) {
		return modelMapper.map(request, Produto.class);
	}


	@PutMapping("/destacar/{idProduto}")
	@ResponseStatus(NO_CONTENT)
	public void destacarProduto(@RequestBody ProdutoDestaque request,
	                            @PathVariable Long idProduto) {
		Produto produto = produtoService.procurarProdutoPeloid(idProduto);
		produtoService.destacarProduto(produto, request.getNivelDestaque());
	}

	@DeleteMapping("/{idProduto}")
	@ResponseStatus(NO_CONTENT)
	public void removeProduto(@PathVariable Long idProduto) {
		produtoService.removeProduto(idProduto);
	}

	@GetMapping(value = "/imagem/{id}/{imagemEspecifica}")
	public ImagemDTO getProdutoImagem(@PathVariable Long id, @PathVariable int imagemEspecifica) {
		ImagemProduto imagemOptional = produtoService.pegarImagem(id, imagemEspecifica);
		ImagemDTO imagemDTO = new ImagemDTO();
		imagemDTO.setImagem(imagemOptional.getImagem());
		return imagemDTO;
	}

	@GetMapping(value = "/imagem-web/{id}/{imagemEspecifica}")
	public String getProdutoImagemWeb(@PathVariable Long id, @PathVariable int imagemEspecifica) {
		ImagemProduto imagemOptional = produtoService.pegarImagem(id, imagemEspecifica);
		return imagemOptional.getImagem();
	}

	@PostMapping("/imagem-web/multipart/{id}")
	public void postMultipart(@RequestBody @Valid MultipartFile file,@PathVariable Long id) throws IOException {
		byte[] imagem = file.getBytes();
		String imageBase64 = new String(Base64.getMimeEncoder().encode(imagem));
		produtoService.saveToBase64(imageBase64, id);
	}

	@GetMapping(value = "/imagem-web/multipart/{id}/{imagemEspecifica}", produces = "image/jpeg")
	public byte[] getProdutoImagemWebMultpart(@PathVariable Long id, @PathVariable int imagemEspecifica) {
		ImagemProduto imagemOptional = produtoService.pegarImagem(id, imagemEspecifica);
		return Base64.getMimeDecoder().decode(imagemOptional.getImagem());
	}

	@DeleteMapping("/imagem/{id}")
	@ResponseStatus(NO_CONTENT)
	public void deleteImgById(@PathVariable Long id) {
		imagensService.removerImagemPeloId(id);
	}
}
