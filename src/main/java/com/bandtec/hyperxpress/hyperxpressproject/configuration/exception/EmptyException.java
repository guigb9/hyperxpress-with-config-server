package com.bandtec.hyperxpress.hyperxpressproject.configuration.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.Messages.NOT_FOUND_MESSAGE;

public class EmptyException extends RuntimeException {
	private static final Logger LOGGER = LoggerFactory.getLogger(EmptyException.class);

	public EmptyException(String object) {
		super(String.format(NOT_FOUND_MESSAGE, object));
		LOGGER.error("{} não encontrado", object);
	}
}
