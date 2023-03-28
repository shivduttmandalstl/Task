package com.employee.management.system.Task.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.employee.management.system.Task.model.Tasks;
import com.employee.management.system.Task.repository.TaskRepository;


@Service
public class ServiceLayer {
	
	@Autowired
	TaskRepository repository;
	
	public List<Tasks> getTasksByEmail(String email) {
		if(repository.findByEmail(email).isEmpty()) {
			return null;
		}
		else {
			return repository.findByEmail(email);
		}
	}
	
	public ResponseEntity<Tasks> addTask(Tasks task ){
		if(task.getDueDate().isBefore(LocalDate.now())) {
			return new ResponseEntity<Tasks>(HttpStatus.NOT_ACCEPTABLE);			
		}
		else if (repository.existsByEmailAndTask(task.getEmail(),task.getTask())) {
			return new ResponseEntity<Tasks>(HttpStatus.ALREADY_REPORTED);
		}
	
		else{
			task.setManagerEmail(repository.getManagerEmail(task.getEmail()));
			task.setManagerName(repository.getManagerName(task.getEmail()));
			return new ResponseEntity<Tasks>(repository.save(task),HttpStatus.CREATED);	
		}
	}
	
	public ResponseEntity<Tasks> deleteTaskById(int taskId){
		if(repository.existsById(taskId)) {
			repository.deleteById(taskId);
			return new ResponseEntity<Tasks>(HttpStatus.OK);
		}
		else {
			return new ResponseEntity<Tasks>(HttpStatus.NOT_FOUND);
		}
	}
	
	public ResponseEntity<Tasks> updateTask(Tasks task ) {	
			return new ResponseEntity<Tasks>(repository.save(task),HttpStatus.OK);
		
	}

	public Optional<Tasks> getTasksByTaskId(int taskId) {
		return repository.findById(taskId);
	}
	
	public List<Tasks> findTasksByManager(String managerEmail){
		return repository.findByManagerEmail(managerEmail);
	}
}
