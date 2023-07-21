package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.control.service.FormaPagamentoService;
import com.bandtec.hyperxpress.hyperxpressproject.model.entity.FormaPagamento;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.FormaPagamentoRepository;
import org.springframework.stereotype.Service;

@Service
public class FormaPagamentoServiceImpl implements FormaPagamentoService {

	private final FormaPagamentoRepository formaPagamentoRepository;

	public FormaPagamentoServiceImpl(FormaPagamentoRepository formaPagamentoRepository) {
		this.formaPagamentoRepository = formaPagamentoRepository;
	}

	public FormaPagamento pegarFormaPagamento(Integer idFormaPagamento) {
		return formaPagamentoRepository.findById(idFormaPagamento).orElseThrow(() -> new EmptyException("Forma de Pagamento"));
	}
}
