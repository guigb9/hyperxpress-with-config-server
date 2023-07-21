package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;


import com.bandtec.hyperxpress.hyperxpressproject.model.entity.Suporte;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.SuporteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SuporteService {
	@Autowired
	private SuporteRepository repository;

	public Page<Suporte> obterChamados(Pageable pageable) {
		Page<Suporte> suporte = repository.findAll(pageable);
		return suporte;
	}

	public Optional<Suporte> obterChamadosPorId(Integer id) {
		Optional<Suporte> chamado = repository.findById(id);
		return chamado;
	}

	public Suporte atenderChamado(Integer id) {
		Suporte suporte = repository.findById(id).get();
		suporte.setChamadoAberto(false);
		repository.save(suporte);
		return suporte;
	}

	public void salvarChamado(Suporte chamado) {
		repository.save(chamado);
	}

	public Boolean deletarChamado(Integer id) {
		if (repository.existsById(id)) {
			repository.deleteById(id);
			return true;
		}
		return false;
	}


}
