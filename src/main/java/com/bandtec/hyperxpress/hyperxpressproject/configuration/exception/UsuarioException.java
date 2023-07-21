package com.bandtec.hyperxpress.hyperxpressproject.configuration.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UsuarioException extends RuntimeException {
	private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioException.class);
	private String message;

	public UsuarioException(String message) {
		this.message = message;
	}

	@Override
	public String getMessage() {
		LOGGER.error(message);
		return message;
	}
}
