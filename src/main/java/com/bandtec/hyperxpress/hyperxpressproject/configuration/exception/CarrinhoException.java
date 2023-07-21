package com.bandtec.hyperxpress.hyperxpressproject.configuration.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarrinhoException extends RuntimeException {
	private static final Logger LOGGER = LoggerFactory.getLogger(CarrinhoException.class);

	public CarrinhoException() {
		super("Carrinho vazio.");
		LOGGER.error("Carrinho vazio.");
	}
}
