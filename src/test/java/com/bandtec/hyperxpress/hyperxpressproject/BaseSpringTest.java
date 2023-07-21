package com.bandtec.hyperxpress.hyperxpressproject;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = {"classpath:application-test.properties"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public abstract class BaseSpringTest {
	@Autowired
	private WebApplicationContext webApplicationContext;

	protected RequestSpecification requestSpecificationToMerge = new RequestSpecBuilder()
			.setContentType(ContentType.JSON)
			.build();

	@BeforeAll
	public void setUp() {
		RestAssuredMockMvc.webAppContextSetup(webApplicationContext);
		RestAssuredMockMvc.enableLoggingOfRequestAndResponseIfValidationFails(LogDetail.ALL);
	}

	protected ResponseSpecification expectJsonMap(String jsonPath) {
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		JsonPath expectedJson = getJsonFromResource(jsonPath);
		builder.expectBody("", equalTo(expectedJson.getMap("")));
		return builder.build();
	}

	protected ResponseSpecification expectFieldErrors(List<String> expectFields) {
		ResponseSpecBuilder builder = new ResponseSpecBuilder();
		builder.expectBody("fieldErrors.size()", is(expectFields.size()));
		for (String field : expectFields) {
			builder.expectBody("fieldErrors.collect { it.key }", hasItem(field));
		}
		builder.expectStatusCode(HttpStatus.BAD_REQUEST.value());

		return builder.build();
	}

	private JsonPath getJsonFromResource(String jsonPath) {
		return new JsonPath(Thread.currentThread().getContextClassLoader().getResource(jsonPath));
	}
}
