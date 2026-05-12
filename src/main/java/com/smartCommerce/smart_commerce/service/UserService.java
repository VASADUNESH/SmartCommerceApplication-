package com.smartCommerce.smart_commerce.service;

import java.util.List;

import com.smartCommerce.smart_commerce.dto.request.RoleUpdateRequest;
import com.smartCommerce.smart_commerce.dto.request.UserRequest;
import com.smartCommerce.smart_commerce.dto.response.UserResponse;
import com.smartCommerce.smart_commerce.model.enums.UserRoles;

import jakarta.validation.Valid;

public interface UserService {

	UserResponse registerUser(@Valid UserRequest userRequest);

	UserResponse getUserById(String id);

	List<UserResponse> getAllUsers();

	List<UserResponse> getAllUsersByEmail(String email);

	List<UserResponse> getAllUsersByRole(UserRoles roles);

	UserResponse updateUserDetails(String id, @Valid UserRequest userRequest);

	void deleteUser(String id);

	void deactiveUser(String id);

	List<UserResponse> searchUsers(String name);

	UserResponse updateUserRoleById(String id, UserRoles roleUpdateRequest);

}
