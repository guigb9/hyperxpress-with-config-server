package com.bandtec.hyperxpress.hyperxpressproject.configuration.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProdutoException extends RuntimeException {
	private static final Logger LOGGER = LoggerFactory.getLogger(ProdutoException.class);

	public ProdutoException() {
		super("Coloque um nivel de destaque de 1 a 3.");
		LOGGER.error("Coloque um nivel de destaque de 1 a 3.");
	}

	public ProdutoException(String message) {
		super(message);
		LOGGER.error(message);
	}
}
