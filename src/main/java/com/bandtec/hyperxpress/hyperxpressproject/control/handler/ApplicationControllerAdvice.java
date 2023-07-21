package com.bandtec.hyperxpress.hyperxpressproject.control.handler;

import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.CarrinhoException;
import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.EmptyException;
import com.bandtec.hyperxpress.hyperxpressproject.configuration.exception.ProdutoException;
import com.bandtec.hyperxpress.hyperxpressproject.control.ApiErrors;
import com.mercadopago.exceptions.MPConfException;
import com.mercadopago.exceptions.MPException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.mail.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	@ExceptionHandler(EmptyException.class)
	@ResponseStatus(NOT_FOUND)
	public ApiErrors hendleEmptyException(EmptyException emptyException) {
		String mensagemErro = emptyException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(MPConfException.class)
	@ResponseStatus(NOT_FOUND)
	public ApiErrors hendleEmptyException(MPConfException mpConfException) {
		String mensagemErro = mpConfException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(ProdutoException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors hendleProdutoException(ProdutoException produtoException) {
		String mensagemErro = produtoException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(CarrinhoException.class)
	@ResponseStatus(NO_CONTENT)
	public ApiErrors hendleCarrinhoException(CarrinhoException carrinhoException) {
		String mensagemErro = carrinhoException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(MPException.class)
	@ResponseStatus(NOT_FOUND)
	public ApiErrors hendleEmptyException(MPException mpException) {
		String mensagemErro = mpException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(MailException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors hendleMailException(MailException mailException) {
		String mensagemErro = mailException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(MailAuthenticationException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors hendleMailAuthenticationException(MailAuthenticationException mailException) {
		String mensagemErro = mailException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(MailParseException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors hendleMailParseException(MailParseException mailException) {
		String mensagemErro = mailException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(MailSendException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors hendleSendException(MailSendException mailException) {
		String mensagemErro = mailException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(MailPreparationException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors hendleMailPreparationException(MailPreparationException mailException) {
		String mensagemErro = mailException.getMessage();
		return new ApiErrors(mensagemErro);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(BAD_REQUEST)
	public ApiErrors handleMethodNotValidException(MethodArgumentNotValidException ex) {
		return new ApiErrors(ex.getBindingResult()
				.getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.toList()));
	}
}
