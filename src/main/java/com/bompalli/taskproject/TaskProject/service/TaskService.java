package com.bompalli.taskproject.TaskProject.service;

import java.util.List;

import com.bompalli.taskproject.TaskProject.payload.TaskDTO;

public interface TaskService {

	public TaskDTO saveTask(long userid,TaskDTO taskDto);
	
	public List<TaskDTO> getAllTasks(long userid);
	
	public TaskDTO getTask(long userid,long taskid);

	public void deleteTask(long userid,long taskid);
}
