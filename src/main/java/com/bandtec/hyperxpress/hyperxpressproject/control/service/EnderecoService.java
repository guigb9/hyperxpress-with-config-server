package com.bandtec.hyperxpress.hyperxpressproject.control.service;

import com.bandtec.hyperxpress.hyperxpressproject.control.request.AtualizacaoEnderecoRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.CadastroUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Endereco;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EnderecoService {
	Endereco procurarEnderecoPeloCodigoUsuario(Long idUsuario);

	Page<Endereco> findAllEnderecos(Endereco filter, Pageable pageable);

	Page<Endereco> enderecoNumeroCep(String cep, String number, Pageable pageable);

	void criarEndereco(CadastroUsuarioRequest cadastro, Usuario usuario);

	Endereco setInformacoesEndereco(CadastroUsuarioRequest cadastro, Usuario codigoUsuario, Endereco endereco);

	void atualizarEnderecoPeloIdUsuario(CadastroUsuarioRequest endereco, Long idUser);

	void atualizarEnderecoPeloIdUsuarioComAtualizacaoEnderecoRequest(AtualizacaoEnderecoRequest endereco, Usuario usuario);
}
