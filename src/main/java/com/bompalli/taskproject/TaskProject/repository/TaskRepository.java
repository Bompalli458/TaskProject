 package com.bompalli.taskproject.TaskProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bompalli.taskproject.TaskProject.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {

	public List<Task> findAllByUsersId(long userid);


	
}
