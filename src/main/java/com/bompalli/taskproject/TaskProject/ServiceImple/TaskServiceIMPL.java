package com.bompalli.taskproject.TaskProject.ServiceImple;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bompalli.taskproject.TaskProject.entity.Task;
import com.bompalli.taskproject.TaskProject.entity.Users;
import com.bompalli.taskproject.TaskProject.exception.APIException;
import com.bompalli.taskproject.TaskProject.exception.TaskNotFound;
import com.bompalli.taskproject.TaskProject.exception.UserNotFound;
import com.bompalli.taskproject.TaskProject.payload.TaskDTO;
import com.bompalli.taskproject.TaskProject.repository.TaskRepository;
import com.bompalli.taskproject.TaskProject.repository.UserRepository;
import com.bompalli.taskproject.TaskProject.service.TaskService;

@Service
public class TaskServiceIMPL implements TaskService {

		
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	
	@Override
	public TaskDTO saveTask(long userid, TaskDTO taskDto) {
		Users user=userRepository.findById(userid).orElseThrow(()->new UserNotFound(String.format("User ID %d not found", userid)));
		Task task=modelMapper.map(taskDto, Task.class);
		task.setUsers(user);
		Task savedTask=taskRepository.save(task);
		return modelMapper.map(savedTask, TaskDTO.class);
	}

	@Override
	public List<TaskDTO> getAllTasks(long userid) {
		userRepository.findById(userid).orElseThrow(()->new UserNotFound(String.format("User ID %d not found", userid)));
		
	List<Task> tasks=taskRepository.findAllByUsersId(userid);
	
	return  tasks.stream().map(
				task -> modelMapper.map(tasks, TaskDTO.class)).collect(Collectors.toList());
	   //  return taskDtos;
	}

	@Override
	public TaskDTO getTask(long userid, long taskid) {
		Users user=userRepository.findById(userid).orElseThrow(()->new UserNotFound(String.format("User ID %d not found", userid)));
		Task task=taskRepository.findById(taskid).orElseThrow(()->new TaskNotFound(String.format("Task ID %d not found", taskid)));
 		if(user.getId()!=task.getUsers().getId()) {
			 throw new APIException(String.format("Task id %d is not belongs to user id %d", taskid,userid));
		}
		return modelMapper.map(task, TaskDTO.class);
	}

	@Override  //delete specific task
	public void deleteTask(long userid, long taskid) {
		Users user=userRepository.findById(userid).orElseThrow(()->new UserNotFound(String.format("User ID %d not found", userid)));
		Task task=taskRepository.findById(taskid).orElseThrow(()->new TaskNotFound(String.format("Task ID %d not found", taskid)));
		if(user.getId()!=task.getUsers().getId()) {
			 throw new APIException(String.format("Task id %d is not belongs to user id %d", taskid,userid));
	}
		taskRepository.deleteById(taskid);
}
} 
