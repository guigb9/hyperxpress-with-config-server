package com.bandtec.hyperxpress.hyperxpressproject.control.service;

import com.bandtec.hyperxpress.hyperxpressproject.control.request.AtualizacaoUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.CadastroUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.ImagemRequest;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.UsuarioLoginDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;

public interface UsuarioService {
	UsuarioLoginDTO toUsuarioLoginDTO(Usuario u);

	Usuario procurarUsuarioPeloId(Long idUsuario);

	Page<Usuario> pegarTodosUsuarios(Pageable pageable);

	Usuario salvarUsuario(CadastroUsuarioRequest cadastro);

	UsuarioLoginDTO login(String email, String senha);

	String pegarImagemUsuario(Long id);

	Usuario verificarSeEmailExiste(String email);

	void cadastrarImagemUsuario(Long idUsuario, ImagemRequest imagem) throws IOException;

	void atualizarInformacaoUsuario(Long idUsuario, CadastroUsuarioRequest usuario);

	void saveImagemBase64(Usuario procurarUsuarioPeloId, String imagemDecodificada);

	void atualizaInfoUser(Long idUsuario, AtualizacaoUsuarioRequest request);
}
