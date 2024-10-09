package com.security.application.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.security.application.model.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, Integer>{
	
	Users findByUsername(String username);
	
	Optional<Users> findById(String id);

}
