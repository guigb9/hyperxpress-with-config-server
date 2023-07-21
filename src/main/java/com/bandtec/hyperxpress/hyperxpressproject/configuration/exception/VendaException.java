package com.bandtec.hyperxpress.hyperxpressproject.configuration.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class VendaException extends RuntimeException {
	@Override
	public String getMessage() {
		return "Produto não encontrado";
	}
}
