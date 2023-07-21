package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.control.request.AvaliacaoUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.AvaliacoesUsuarioService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.AvaliacoesUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.AvaliacoesUsuarioRepository;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.AvaliacaoUsuarioDTO;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class AvaliacoesUsuarioServiceImpl implements AvaliacoesUsuarioService {

	private final AvaliacoesUsuarioRepository repositoryAvaliacao;
	private final ModelMapper modelMapper;

	public AvaliacoesUsuarioServiceImpl(AvaliacoesUsuarioRepository repositoryAvaliacao, ModelMapper modelMapper) {
		this.repositoryAvaliacao = repositoryAvaliacao;
		this.modelMapper = modelMapper;
	}

	public AvaliacoesUsuario salvarAvaliacao(AvaliacoesUsuario avaliacao) {
		return repositoryAvaliacao.save(avaliacao);
	}


	public AvaliacoesUsuario setarInformacoes(AvaliacaoUsuarioRequest request) {
		return toAvaliacoesUsuario(request);
	}

	public AvaliacoesUsuario toAvaliacoesUsuario(AvaliacaoUsuarioRequest request) {
		return modelMapper.map(request, AvaliacoesUsuario.class);
	}

	public Page<AvaliacaoUsuarioDTO> pegarAvaliacoesPorUsuario(Long idUsuario, Pageable pageable) {
		Page<AvaliacaoUsuarioDTO> avaliacoesUsuario = avaliacoesPorUsuario(idUsuario, pageable);
		if (avaliacoesUsuario.isEmpty()) {
			log.error("Avaliações para usuário de id {} nao encontradas.", idUsuario);
			throw new ResponseStatusException(HttpStatus.NO_CONTENT,"Avaliacao para Usuario");
		}
		return avaliacoesUsuario;
	}

	public Double calcularMediaEstrelas(Usuario usuario) {
		return repositoryAvaliacao.calcMediaEstrelas(usuario);
	}

	@Transactional
	public void remocaoDeAvaliacao(Long idAvaliacao) {
		removerChaves(idAvaliacao);
		repositoryAvaliacao.deleteById(idAvaliacao);
	}

	public Page<AvaliacaoUsuarioDTO> avaliacoesPorUsuario(Long idUsuario, Pageable pageable) {
		return repositoryAvaliacao.
				findByUsuarioAvaliadoId(idUsuario, pageable).map(this::toUsuarioDTO);
	}

	public AvaliacaoUsuarioDTO toUsuarioDTO(AvaliacoesUsuario avaliacoesUsuario) {
		return modelMapper.map(avaliacoesUsuario, AvaliacaoUsuarioDTO.class);
	}

	public void removerChaves(Long idAvaliacao) {
		Optional<AvaliacoesUsuario> avaliacoesUsuario = repositoryAvaliacao.findById(idAvaliacao);
		if (avaliacoesUsuario.isPresent()) {
			avaliacoesUsuario.get().setUsuarioAvaliado(null);
			avaliacoesUsuario.get().setUsuarioAvaliado(null);
		} else {
			log.error("Avaliação de id {} nao encontrado.", idAvaliacao);
			throw new EmptyException("Usuario");
		}
	}
}
