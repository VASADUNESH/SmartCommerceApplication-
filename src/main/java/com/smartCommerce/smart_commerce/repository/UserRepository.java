package com.smartCommerce.smart_commerce.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.smartCommerce.smart_commerce.dto.response.UserResponse;
import com.smartCommerce.smart_commerce.model.User;
import com.smartCommerce.smart_commerce.model.enums.UserRoles;


public interface UserRepository extends MongoRepository<User, String>{

	List<User> findByActiveTrue();

	List<User> findByEmail(String email);
	
	List<User> findByRole(UserRoles role);

	boolean existsByEmail(String email);
	
	List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String name, String name2);
	
}
