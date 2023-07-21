package com.bandtec.hyperxpress.hyperxpressproject.control.controller;


import com.bandtec.hyperxpress.hyperxpressproject.control.service.MercadoPagoService;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.PedidoDTO;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.RetornoMpDTO;
import com.mercadopago.resources.Preference;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mercado-pago")
@RequiredArgsConstructor
public class MercadoPagoController {

	private final MercadoPagoService mercadoPagoService;

	@SneakyThrows
	@PostMapping("/pagar-pedido")
	@ResponseStatus(HttpStatus.CREATED)
	public RetornoMpDTO pagarPedido(@RequestBody PedidoDTO pedido) {
		mercadoPagoService.setarTokenDeAcesso();
		Preference preference = mercadoPagoService.criarPreferencia(pedido);
		return new RetornoMpDTO(preference);
	}
}