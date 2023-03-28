package com.employee.management.system.Task.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.system.Task.model.Tasks;
import com.employee.management.system.Task.service.ServiceLayer;


@RestController
@RequestMapping(path="/task")
@CrossOrigin(origins = "*")
public class HomeController {
	@Autowired
	ServiceLayer service;
	
	@GetMapping(path = "/all/{email}")
	public List<Tasks> getTasksByEmail(@PathVariable String email) {
		return service.getTasksByEmail(email);
	}
	
	@GetMapping(path = "/id/{taskId}")
	public Optional<Tasks> getTasksByEmail(@PathVariable int taskId) {
		return service.getTasksByTaskId(taskId);
	}
	
	@PostMapping(path = "/add")
	public @ResponseBody ResponseEntity<Tasks> addLeave(@RequestBody Tasks task ){
		return service.addTask(task);
	}
	
	@DeleteMapping(path = "/delete/{taskId}")
	public ResponseEntity<Tasks> DeleteLeaveByLeaveId(@PathVariable int taskId){
		return service.deleteTaskById(taskId);
	}
	
	@PutMapping(path = "/update")
	public ResponseEntity<Tasks> updateLeave(@RequestBody Tasks task ) {
		return service.updateTask(task);
	}
	
	
	@GetMapping(path = "/manager/{managerEmail}")
	public List<Tasks> getTasksByManagerEmail(@PathVariable String managerEmail) {
		return service.findTasksByManager(managerEmail);
	}
	
	
	
	
}
