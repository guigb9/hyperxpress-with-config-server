package com.bandtec.hyperxpress.hyperxpressproject.control.service;

import com.bandtec.hyperxpress.hyperxpressproject.view.dto.PedidoDTO;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;

public interface MercadoPagoService {
	void setarTokenDeAcesso() throws MPConfException;

	Preference criarPreferencia(PedidoDTO pedidoDTO) throws MPException;
}
