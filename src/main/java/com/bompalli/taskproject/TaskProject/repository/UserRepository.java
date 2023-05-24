package com.bompalli.taskproject.TaskProject.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bompalli.taskproject.TaskProject.entity.Users;


public interface UserRepository extends JpaRepository<Users, Long> {

	Optional <Users> findByEmail(String email);

}
