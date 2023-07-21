package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.AtualizacaoUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.CadastroUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.ImagemRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.EnderecoService;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.UsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.ImagemUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.UsuarioRepository;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.UsuarioLoginDTO;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService {

	private final UsuarioRepository repository;
	private final EnderecoService enderecoService;
	private final ImagensService imagensService;
	private final ModelMapper modelMapper;

	public Usuario criarUsuario(CadastroUsuarioRequest cadastro) {
		Usuario usuario = new Usuario();
		usuario = setInformacoesUsuario(cadastro, usuario);
		return usuario;
	}

	public Usuario setInformacoesUsuario(CadastroUsuarioRequest cadastro, Usuario usuario) {
		usuario.setNome(cadastro.getNome());
		usuario.setAvatar(cadastro.getAvatar());
		usuario.setCpf(cadastro.getCpf());
		usuario.setEmail(cadastro.getEmail());
		usuario.setSenha(cadastro.getSenha());
		usuario.setDataNasc(cadastro.getDataNasc());
		usuario.setEmailConfirmado(false);
		return usuario;
	}


	public Optional<Usuario> buscarUsuarioPorEmailESenha(String email, String senha) {
		return repository.findByEmailAndSenha(email, senha);
	}

	public UsuarioLoginDTO toUsuarioLoginDTO(Usuario usuario) {
		return modelMapper.map(usuario, UsuarioLoginDTO.class);
	}

	public void cadastrarImagemUsuario(Long idUsuario, ImagemRequest imagem) {
		Usuario usuario = procurarUsuarioPeloId(idUsuario);
		ImagemUsuario imgUser = imagensService.setarInfoUsuarioImagens(usuario, imagem);
		imagensService.salvarImagem(imgUser);

	}

    @Override
    public void atualizarInformacaoUsuario(Long idUsuario, CadastroUsuarioRequest usuarioNovo) {
		Usuario user = repository.findById(idUsuario).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,"Usuario não encontrado"));
		Usuario usuario = setInformacoesUsuario(usuarioNovo, user);
		repository.save(usuario);
		enderecoService.atualizarEnderecoPeloIdUsuario(usuarioNovo, idUsuario);
    }

    public void saveImagemBase64(Usuario usuario, String imagemDecodificada){
		imagensService.salvarImagemUsuario(imagemDecodificada, usuario);
    }

    @Transactional
	public void atualizaInfoUser(Long idUsuario, AtualizacaoUsuarioRequest request) {
		Usuario usuario = modelMapper.map(request, Usuario.class);
		usuario.setId(idUsuario);
		repository.save(usuario);
	}

	public Usuario salvarUsuario(CadastroUsuarioRequest cadastro) {
	    if (repository.findByEmail(cadastro.getEmail()).isPresent()) {
		    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuário já cadastrado no sistema");
	    }
	    Usuario usuario = criarUsuario(cadastro);
	    repository.save(usuario);
	    enderecoService.criarEndereco(cadastro, procurarUsuarioPeloId(usuario.getId()));
		return usuario;
	}


	public UsuarioLoginDTO login(String login, String senha) {
		Usuario user = buscarUsuarioPorEmailESenha(login, senha).orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Email ou senha incorreto"));
		return toUsuarioLoginDTO(user);
	}

	public String pegarImagemUsuario(Long id) {
		ImagemUsuario imgUsuario = imagensService.imagemUsuarioAssociado(id).orElseThrow(() -> new EmptyException("Imagem usuario"));
		return imgUsuario.getConteudoImagem();
	}

	public Usuario verificarSeEmailExiste(String email) {
		return repository.findByEmail(email).orElseThrow(() -> new EmptyException("Email do usuário"));
	}

	public Usuario procurarUsuarioPeloId(Long idUsuario) {
		return repository.findById(idUsuario).orElseThrow(() -> new EmptyException("Usuário"));
	}

	public Page<Usuario> pegarTodosUsuarios(Pageable pageable) {
		return repository.findAll(pageable);
	}


}
