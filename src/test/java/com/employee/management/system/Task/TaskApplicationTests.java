package com.employee.management.system.Task;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.employee.management.system.Task.model.Tasks;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

@SpringBootTest
class TaskApplicationTests {

	@Test
	void contextLoads() {
	}

//	It will first get token then use this for generation of new Task then verify it with get request
//	then update Progress then delete the data
	@Test
	void allTaskFunctionalityTest() throws JSONException {
		
		String authCheck="{ \"email\":\"ashutosh@gmail.com\", \"password\":\"ashutosh\", \"role\":\"MANAGER\"}";
		
		String response = given().header("Content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
                .body(authCheck)
                .when()
                .post("http://localhost:9000/home/authenticate")
                .then()
                .assertThat().statusCode(200)
                .extract().response().asString();
		RestAssured.defaultParser = Parser.JSON;
		JSONObject jsonToken;
        jsonToken = new JSONObject(response);
        
        Tasks task = new Tasks();
        LocalDate due = LocalDate.now().plusDays(5);
        
        task.setEmail("kritika@gmail.com");
        task.setDueDate(due);
        task.setTask("Random Test Task");
        
        String token = "Bearer " + jsonToken.getString("token");

// 		Add Task test     
        Tasks responsePost = given()
        .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
        .body(task)
        .when()
        .post("http://localhost:9002/task/add")
        .then()
        .assertThat().statusCode(201)
        .extract().response().getBody().as(Tasks.class);

//      Read Tasks Test
        Tasks resultSaved = given()
       .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
       .when()
       .get("http://localhost:9002/task/id/"+responsePost.getTaskId())
       .then()
       .assertThat().statusCode(200)
       .extract().response().getBody().as(Tasks.class);
            
        assertEquals(task.getEmail(), resultSaved.getEmail());
		assertEquals(task.getDueDate(), resultSaved.getDueDate());
		assertEquals(task.getTask(), resultSaved.getTask());
		
//		Update Task Progress Test
		resultSaved.setProgress(4);		
		
		Tasks responsePut = given()
		        .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
		        .body(resultSaved)
		        .when()
		        .put("http://localhost:9002/task/update")
		        .then()
		        .assertThat().statusCode(200)
		        .extract().response().getBody().as(Tasks.class);
		
		assertEquals(4, responsePut.getProgress());
		
		
		
//		Delete Leave Test
		 given()
         .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
         .when()
         .delete("http://localhost:9002/task/delete/"+responsePost.getTaskId())
         .then()
         .assertThat().statusCode(200)
         .extract().response();
		 
	}
	
}
