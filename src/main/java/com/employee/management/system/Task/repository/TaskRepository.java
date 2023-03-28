package com.employee.management.system.Task.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.employee.management.system.Task.model.Tasks;

public interface TaskRepository extends JpaRepository<Tasks, Integer>{

	List<Tasks> findByEmail(String email); 
	List<Tasks> findByManagerEmail(String managerEmail);
	Boolean existsByEmailAndTask(String email,String task);
	
	@Query(value="select email from users where email =:e",nativeQuery = true)
	Optional<String> getUserDetails(@Param("e") String email);
	
	@Query(value = "select users.manager_email from users where users.email=:e",nativeQuery = true)
	String getManagerEmail(@Param("e") String email);
	
	@Query(value = "select users.manager_name from users where users.email=:e",nativeQuery = true)
	String getManagerName(@Param("e") String email);
	
	@Query(value = "select manager_email from tasks where task_id=:i",nativeQuery = true)
	String getManagerEmailByTaskId(@Param("i") int taskId);
	
	
	
}
