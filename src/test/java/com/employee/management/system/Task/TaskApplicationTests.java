package com.employee.management.system.Task;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDate;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.boot.test.context.SpringBootTest;
import com.employee.management.system.Task.model.Tasks;
import io.restassured.http.ContentType;



@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class TaskApplicationTests {

	@Test
	void contextLoads() {
	}
	
	String AuthVerification() throws JSONException {
		String authCheck="{ \"email\":\"ashutosh@gmail.com\", \"password\":\"ashutosh\", \"role\":\"MANAGER\"}";
		
		String response = given().header("Content-type", "application/json").contentType(ContentType.JSON).accept(ContentType.JSON)
	            .body(authCheck)
	            .when()
	            .post("http://localhost:9000/home/authenticate")
	            .then()
	            .assertThat().statusCode(200)
	            .extract().response().asString();
		
		JSONObject jsonToken = new JSONObject();
	    jsonToken = new JSONObject(response);
	    
	    String tokenAuth = "Bearer " + jsonToken.getString("token");
		return tokenAuth;
	}
	

	static int taskId;

//	It will test the functionality that leave is adding or not.
	@Test
	@Order(1)
	void AddTaskTest() throws JSONException {
	       
        Tasks task = new Tasks();
        LocalDate due = LocalDate.now().plusDays(5);
        
        task.setEmail("kritika@gmail.com");
        task.setDueDate(due);
        task.setTask("Random Test Task");
        
        String token = AuthVerification();
    
        Tasks responsePost = given()
        .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
        .body(task)
        .when()
        .post("http://localhost:9002/task/add")
        .then()
        .assertThat().statusCode(201)
        .extract().response().getBody().as(Tasks.class);
        
        TaskApplicationTests.taskId = responsePost.getTaskId();
        
        
        assertEquals(task.getEmail(), responsePost.getEmail());
		assertEquals(task.getDueDate(), responsePost.getDueDate());
		assertEquals(task.getTask(), responsePost.getTask());
	}
	
	

//	Read Tasks Test
	@Test
	@Order(2)
	void GetTaskTest() throws JSONException {
	    
        Tasks task = new Tasks();
        LocalDate due = LocalDate.now().plusDays(5);
        
        task.setEmail("kritika@gmail.com");
        task.setDueDate(due);
        task.setTask("Random Test Task");
        
        String token = AuthVerification();
					
        Tasks resultSaved = given()
       .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
       .when()
       .get("http://localhost:9002/task/id/"+taskId)
       .then()
       .assertThat().statusCode(200)
       .extract().response().getBody().as(Tasks.class);
            
        assertEquals(task.getEmail(), resultSaved.getEmail());
		assertEquals(task.getDueDate(), resultSaved.getDueDate());
		assertEquals(task.getTask(), resultSaved.getTask());
	}
		
//	Update Task Progress Test
	@Test
	@Order(3)
	void UpdateTaskTest() throws JSONException {
		
		String token = AuthVerification();
		
		Tasks resultSaved = given()
			       .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
			       .when()
			       .get("http://localhost:9002/task/id/"+taskId)
			       .then()
			       .assertThat().statusCode(200)
			       .extract().response().getBody().as(Tasks.class);
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
		
	}
	
//	Get Task by Email Test
	@Test
	@Order(5)
	void GetTaskByEmailTest() throws JSONException {
	String token = AuthVerification();
	String email = "kritika@gmail.com";
	Tasks[] responseTasks = given()
     .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
     .when()
     .get("http://localhost:9002/task/all/"+email)
     .then()
     .assertThat().statusCode(200)
     .extract().response().as(Tasks[].class);
	
	for(int i =0; i<responseTasks.length;i++) {
		assertEquals(email,responseTasks[i].getEmail());
	}
	
	}
	
//	Get Task by Manager Email Test
	@Test
	@Order(5)
	void GetTaskByManagerEmailTest() throws JSONException {
	String token = AuthVerification();
	String managerEmail = "ashutosh@gmail.com";
	Tasks[] responseTasks = given()
     .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
     .when()
     .get("http://localhost:9002/task/manager/"+managerEmail)
     .then()
     .assertThat().statusCode(200)
     .extract().response().as(Tasks[].class);
	
	for(int i =0; i<responseTasks.length;i++) {
		assertEquals(managerEmail,responseTasks[i].getManagerEmail());
	}
	

	 
}
		
//		Delete Task Test
	@Test
	@Order(6)
	void DeleteTaskTest() throws JSONException {
		String token = AuthVerification();
		given()
         .header("Authorization", token).contentType(ContentType.JSON).accept(ContentType.JSON)
         .when()
         .delete("http://localhost:9002/task/delete/"+taskId)
         .then()
         .assertThat().statusCode(200)
         .extract().response();
		 
	}
	
}
