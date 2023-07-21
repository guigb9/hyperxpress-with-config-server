package com.bandtec.hyperxpress.hyperxpressproject.control.service.impl;

import com.bandtec.hyperxpress.hyperxpressproject.control.service.MercadoPagoService;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.PedidoDTO;
import com.mercadopago.MercadoPago;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import com.mercadopago.resources.datastructures.preference.BackUrls;
import com.mercadopago.resources.datastructures.preference.Item;
import org.springframework.stereotype.Service;

@Service
public class MercadoPagoServiceImpl implements MercadoPagoService {

	public void setarTokenDeAcesso() throws MPConfException {
		final String TOKEN = "TEST-8099014547724788-090223-0570f323f435ddcb629ed6f612aa3952-434716941";
		MercadoPago.SDK.setAccessToken(TOKEN);
	}

	public Item criarItem(PedidoDTO pedido) {
		Item itemPedido = new Item();
		setarInformacoesItemPedido(itemPedido, pedido);
		return itemPedido;
	}

	public void setarInformacoesItemPedido(Item itemPedido, PedidoDTO pedido) {
		itemPedido.setTitle(pedido.getId().toString())
				.setQuantity(1)
				.setUnitPrice(pedido.getValorTotal()
						.floatValue());
	}

	public Preference criarPreferencia(PedidoDTO pedido) throws MPException {
		Preference preference = new Preference();
		preference.setAutoReturn(Preference.AutoReturn.approved);
		preference.setBackUrls(criarBackUrls(pedido));
		setarInformacoesPreferencia(preference, pedido);
		return preference;
	}

	public BackUrls criarBackUrls(PedidoDTO pedido) {
		BackUrls backUrls = new BackUrls();
		backUrls.setSuccess("http://54.144.215.240/pedidos/sucesso/" + pedido.getId());
		backUrls.setFailure("http://54.144.215.240/pedidos/falha/" + pedido.getId());
		backUrls.setPending("http://54.144.215.240/pedidos/pendente/" + pedido.getId());
		return backUrls;
	}

	public void setarInformacoesPreferencia(Preference preference, PedidoDTO pedido) throws MPException {
		preference.appendItem(criarItem(pedido));
		salvarPreferencia(preference);
	}

	public void salvarPreferencia(Preference preference) throws MPException {
		preference.save();
	}
}
