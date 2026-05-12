package com.smartCommerce.smart_commerce.controller;



import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartCommerce.smart_commerce.dto.request.RoleUpdateRequest;
import com.smartCommerce.smart_commerce.dto.request.UserRequest;
import com.smartCommerce.smart_commerce.dto.response.UserResponse;
import com.smartCommerce.smart_commerce.model.enums.UserRoles;
import com.smartCommerce.smart_commerce.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management APIs")
public class UserController {

	private final UserService userService;
	
	@PostMapping("/register")
	@Operation(summary = "Register a new user")
	public ResponseEntity<UserResponse> register(
			@Valid @RequestBody UserRequest userRequest
			){
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registerUser(userRequest));
		
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get user by id")
	public ResponseEntity<UserResponse> getUserById(
			@Parameter(description = "user id") @PathVariable String id 
			){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getUserById(id));
		
	}
	
	@GetMapping
	@Operation(summary = "Get all users")
	public ResponseEntity<List<UserResponse>> getAllUser(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsers());
	}
	
	@GetMapping("/email/{email}")
	@Operation(summary = "Get all users by email")
	public ResponseEntity<List<UserResponse>> getAllUsersByEmail(@PathVariable String email){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersByEmail(email));
	}
	
	@GetMapping("/roles/{roles}")
	@Operation(summary = "Get all users by role")
	public ResponseEntity<List<UserResponse>> getAllUsersByRoles(@PathVariable UserRoles roles){
		return ResponseEntity.status(HttpStatus.OK).body(userService.getAllUsersByRole(roles));
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update user details")
	public ResponseEntity<UserResponse> updateUserDetails(
			@PathVariable String id, @Valid @RequestBody UserRequest userRequest
			){
		
		return ResponseEntity.status(HttpStatus.OK).body(userService.updateUserDetails(id, userRequest));
		
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Soft delete user account")
	public ResponseEntity<Void> deleteUser(@PathVariable String id){
		userService.deleteUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@PatchMapping("/{id}/deactive")
	@Operation(summary = "Deactive user account")
	public ResponseEntity<Void> deactiveUser(@PathVariable String id){
		userService.deactiveUser(id);
		return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/search")
	@Operation(summary = "Search name based on first name or last name")
	public ResponseEntity<List<UserResponse>> searchName(@RequestParam String name){
		return ResponseEntity.ok(userService.searchUsers(name));
	}
	
	@PatchMapping("/{id}/role")
	@Operation(summary = "update user role by id")
	public ResponseEntity<UserResponse> updateUserRoleById(
			@Parameter(description = "User id") @PathVariable String id,
			@Valid @RequestBody RoleUpdateRequest roleUpdateRequest
			){
		return ResponseEntity.ok(userService.updateUserRoleById(id, roleUpdateRequest.getRole()));
	}
}

