package com.employee.management.system.Task.model;


import java.time.LocalDate;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "Tasks")
public class Tasks {
	
	@Id
	@GeneratedValue(generator = "task_gen",strategy = GenerationType.AUTO)
	@SequenceGenerator(name="task_gen",sequenceName = "task_seq",initialValue = 2000,allocationSize = 1)
	@Column(name="taskId")
	private int taskId;
	
	@Column(name="email",nullable = false)
	private String email;
	
	@Column(name="task",nullable = false)
	private String task;
	
	@JsonFormat(pattern="yyyy-MM-dd")
	@Column(name = "dueDate",nullable=false)
	private LocalDate dueDate;
	
	@Column(name="progress")
	private int progress;
	
	@Column(name = "managerEmail")
	private String managerEmail;
	
	@Column(name = "managerName")
	private String managerName;

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public void setManagerEmail(String managerEmail) {
		this.managerEmail = managerEmail;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	
	
	
	
}
