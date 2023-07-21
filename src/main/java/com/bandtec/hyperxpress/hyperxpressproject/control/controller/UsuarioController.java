package com.bandtec.hyperxpress.hyperxpressproject.control.controller;

import com.bandtec.hyperxpress.hyperxpressproject.control.request.AtualizacaoUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.CadastroUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.ImagemRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.UsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.Login;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.UsuarioImagemDTO;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.UsuarioLoginDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {
	private final UsuarioService usuarioBusinessModel;

	@GetMapping(produces = "application/json")
	public Page<Usuario> getUsuarios(@PageableDefault() Pageable pageable) {
		return usuarioBusinessModel.pegarTodosUsuarios(pageable);
	}

	@PostMapping("/imagem/{idUsuario}")
	@ResponseStatus(HttpStatus.CREATED)
	public void postUsuarioImg(@RequestBody(required = false) ImagemRequest imagem, @PathVariable Long idUsuario) throws IOException {
		usuarioBusinessModel.cadastrarImagemUsuario(idUsuario, imagem);
	}

	@GetMapping("/{idUsuario}")
	@ResponseStatus(HttpStatus.OK)
	public Usuario getUsuarioEspecifico(@PathVariable Long idUsuario){
		return usuarioBusinessModel.procurarUsuarioPeloId(idUsuario);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Usuario postUsuario(@Valid @RequestBody CadastroUsuarioRequest cadastro) {
		return usuarioBusinessModel.salvarUsuario(cadastro);
	}

	@PostMapping("/login")
	public UsuarioLoginDTO login(@Valid @RequestBody Login login) {
		return usuarioBusinessModel.login(login.getEmail(), login.getSenha());
	}

	@PostMapping("/imagem/multipart/{id}")
	public void salvarImagemMultipart(@PathVariable Long id, @RequestBody @Valid MultipartFile file) throws IOException {
		byte[] imagem = file.getBytes();
		String imagemDecodificada = new String(Base64.getMimeEncoder().encode(imagem));
		usuarioBusinessModel.saveImagemBase64(usuarioBusinessModel.procurarUsuarioPeloId(id), imagemDecodificada);
	}

	@GetMapping(value = "/imagem/multipart/{id}", produces = "image/jpeg")
	public byte[] pegarImagemMultipart(@PathVariable Long id){
		return Base64.getMimeDecoder().decode(usuarioBusinessModel.pegarImagemUsuario(id));
	}


	@GetMapping(value = "/imagem/{id}")
	public UsuarioImagemDTO getImagemUsuario(@PathVariable Long id) {
		return UsuarioImagemDTO.builder().imagem(usuarioBusinessModel.pegarImagemUsuario(id)).build();
	}

	@GetMapping(value = "/verificar/{email}", produces = "application/json")
	public void verficarEmailIgual(@PathVariable String email) {
		usuarioBusinessModel.verificarSeEmailExiste(email);
	}

	@PutMapping(value = "/{idUsuario}")
	@ResponseStatus(HttpStatus.OK)
	public void atualizaInformacoesUsuario(@PathVariable Long idUsuario, @RequestBody CadastroUsuarioRequest usuario){
		usuarioBusinessModel.atualizarInformacaoUsuario(idUsuario, usuario);
	}

	@PutMapping ("/info/{idUsuario}")
	public void atualizarInfoUserSemEndereco(@PathVariable Long idUsuario,@RequestBody @Valid AtualizacaoUsuarioRequest request){
		usuarioBusinessModel.atualizaInfoUser(idUsuario, request);
	}
}
