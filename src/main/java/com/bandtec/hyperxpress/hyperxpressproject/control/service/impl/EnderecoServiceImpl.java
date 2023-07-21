package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.control.request.AtualizacaoEnderecoRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.CadastroUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.EnderecoService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Endereco;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.EnderecoRepository;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnderecoServiceImpl implements EnderecoService {

	private final EnderecoRepository enderecoRepository;
	private final ModelMapper modelMapper;
	private final UsuarioRepository usuarioRepository;



	public Endereco procurarEnderecoPeloCodigoUsuario(Long idUsuario) {
		Endereco endereco = enderecoRepository.findByCodigoUsuarioId(idUsuario);
		if (endereco == null) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return endereco;
	}

	@Override
	public Page<Endereco> findAllEnderecos(Endereco filter, Pageable pageable) {
		ExampleMatcher matcher = ExampleMatcher
				.matching()
				.withIgnoreCase()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example<Endereco> example = Example.of(filter, matcher);
		Page<Endereco> enderecos = enderecoRepository.findAll(example, pageable);
		if (enderecos.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return enderecos;
	}

	public List<Endereco> enderecos() {
		return enderecoRepository.findAll();
	}

	public Page<Endereco> enderecoNumeroCep(String cep, String numero, Pageable pageable) {
		Page<Endereco> enderecos = enderecoRepository.findByCepAndNumero(cep, numero, pageable);
		if (enderecos.getContent().isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NO_CONTENT);
		}
		return enderecos;
	}

	public void criarEndereco(CadastroUsuarioRequest cadastro, Usuario usuario) {
		Endereco endereco = new Endereco();
		endereco = setInformacoesEndereco(cadastro, usuario, endereco);
		enderecoRepository.save(endereco);
	}

	public Endereco setInformacoesEndereco(CadastroUsuarioRequest cadastro, Usuario codigoUsuario, Endereco endereco) {
		endereco.setEstadoUf(cadastro.getEstadoUf());
		endereco.setCidade(cadastro.getCidade());
		endereco.setCep(cadastro.getCep());
		endereco.setBairro(cadastro.getBairro());
		if (cadastro.getComplemento() != null) {
			endereco.setComplemento(cadastro.getComplemento());
		}
		endereco.setNumero(cadastro.getNumero());
		endereco.setLogradouro(cadastro.getLogradouro());
		endereco.setCodigoUsuario(codigoUsuario);
		return endereco;
	}

	@Override
	public void atualizarEnderecoPeloIdUsuario(CadastroUsuarioRequest endereco, Long idUsuario) {
		Endereco enderecoUpdate = enderecoRepository.findByCodigoUsuarioId(idUsuario);
		enderecoUpdate.setLogradouro(endereco.getLogradouro());
		enderecoUpdate.setNumero(endereco.getNumero());
		enderecoUpdate.setEstadoUf(endereco.getEstadoUf());
		enderecoUpdate.setComplemento(endereco.getComplemento());
		enderecoUpdate.setCidade(endereco.getCidade());
		enderecoUpdate.setCep(endereco.getCep());
		enderecoUpdate.setBairro(endereco.getBairro());
		enderecoRepository.save(enderecoUpdate);
	}

	@Transactional
	public void atualizarEnderecoPeloIdUsuarioComAtualizacaoEnderecoRequest(AtualizacaoEnderecoRequest request, Usuario usuario) {
		Endereco enderecoUsuario = enderecoRepository.findByCodigoUsuarioId(usuario.getId());
		Endereco endereco = modelMapper.map(request, Endereco.class);
		endereco.setCodigoUsuario(usuario);
		endereco.setId(enderecoUsuario.getId());
		enderecoRepository.save(endereco);
	}
}
