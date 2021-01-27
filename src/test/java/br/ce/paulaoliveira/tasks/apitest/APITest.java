package br.ce.paulaoliveira.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI="http://localhost:8080/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
			.get("/todo")
		.then()
			.statusCode(200);
	}
	
	@Test
	public void deveAdicionarTarefaComSucesso() {
		RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2999-06-01\"}" )
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.statusCode(201);
	}

	@Test
	public void naoDeveAdicionarTarefaInvalida() {
		RestAssured.given()
			.body("{ \"task\": \"teste via api\", \"dueDate\": \"2010-06-01\"}" )
			.contentType(ContentType.JSON)
		.when()
			.post("/todo")
		.then()
			.log().all()
			.statusCode(400)
			.body("message", CoreMatchers.is("Due date must not be in past"));
	}

}
