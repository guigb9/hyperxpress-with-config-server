package com.bandtec.hyperxpress.hyperxpressproject.control.service;

import com.bandtec.hyperxpress.hyperxpressproject.control.request.AvaliacaoUsuarioRequest;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.AvaliacoesUsuario;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Usuario;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.AvaliacaoUsuarioDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AvaliacoesUsuarioService {

	AvaliacoesUsuario setarInformacoes(AvaliacaoUsuarioRequest request);

	AvaliacoesUsuario salvarAvaliacao(AvaliacoesUsuario avaliacao);

	Page<AvaliacaoUsuarioDTO> pegarAvaliacoesPorUsuario(Long idUsuario, Pageable pageable);

	void remocaoDeAvaliacao(Long idAvaliacao);

	Double calcularMediaEstrelas(Usuario usuario);
}
