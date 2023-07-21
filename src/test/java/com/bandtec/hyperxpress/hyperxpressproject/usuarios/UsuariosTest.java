package com.bandtec.hyperxpress.hyperxpressproject.usuarios;


import com.bandtec.hyperxpress.hyperxpressproject.BaseSpringTest;
import com.bandtec.hyperxpress.hyperxpressproject.control.controller.UsuarioController;
import com.bandtec.hyperxpress.hyperxpressproject.control.handler.ApplicationControllerAdvice;
import com.bandtec.hyperxpress.hyperxpressproject.model.repository.UsuarioRepository;
import com.bandtec.hyperxpress.hyperxpressproject.view.dto.Login;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.mapper.TypeRef;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.*;


@RunWith(SpringRunner.class)
public class UsuariosTest extends BaseSpringTest {

	@InjectMocks
	private UsuarioController usuarioController;


	@Mock
	private UsuarioRepository usuarioRepository;

	@InjectMocks
	private ApplicationControllerAdvice applicationControllerAdvice;


	@Before
	public void setUpPort(){
		RestAssured.port = 8089;
	}

	@Test
	@DisplayName("Shouldn't login but password is incorrect")
	public void shouldNotLoginButPasswordIsIncorrect() {
		given().body(Login
				.builder()
				.email("minegb9@gmail.com")
				.senha("ANY_PASSWORD")
				.build()).contentType(ContentType.JSON)
				.when().post("/usuarios")
				.then().statusCode(400);
	}

	@Test
	public void shouldNotReturnUsers(){
		String body = given().get("/usuarios").body().prettyPrint();
		Assert.assertEquals(body, "{\n" +
				"    \"content\": [\n" +
				"        \n" +
				"    ],\n" +
				"    \"pageable\": {\n" +
				"        \"sort\": {\n" +
				"            \"unsorted\": true,\n" +
				"            \"sorted\": false,\n" +
				"            \"empty\": true\n" +
				"        },\n" +
				"        \"offset\": 0,\n" +
				"        \"pageNumber\": 0,\n" +
				"        \"pageSize\": 10,\n" +
				"        \"paged\": true,\n" +
				"        \"unpaged\": false\n" +
				"    },\n" +
				"    \"totalPages\": 0,\n" +
				"    \"last\": true,\n" +
				"    \"totalElements\": 0,\n" +
				"    \"size\": 10,\n" +
				"    \"number\": 0,\n" +
				"    \"numberOfElements\": 0,\n" +
				"    \"sort\": {\n" +
				"        \"unsorted\": true,\n" +
				"        \"sorted\": false,\n" +
				"        \"empty\": true\n" +
				"    },\n" +
				"    \"first\": true,\n" +
				"    \"empty\": true\n" +
				"}");
	}
}
