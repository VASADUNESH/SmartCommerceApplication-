package com.smartCommerce.smart_commerce.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.smartCommerce.smart_commerce.dto.request.RoleUpdateRequest;
import com.smartCommerce.smart_commerce.dto.request.UserRequest;
import com.smartCommerce.smart_commerce.dto.response.UserResponse;
import com.smartCommerce.smart_commerce.exception.DuplicateEmailException;
import com.smartCommerce.smart_commerce.exception.UserNotFoundException;
import com.smartCommerce.smart_commerce.model.User;
import com.smartCommerce.smart_commerce.model.enums.UserRoles;
import com.smartCommerce.smart_commerce.repository.UserRepository;
import com.smartCommerce.smart_commerce.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse registerUser(@Valid UserRequest userRequest) {

        log.info("Registering a new user with email: {}", userRequest.getEmail());

        User user = User.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .phone(userRequest.getPhone())
                .role(UserRoles.CUSTOMER)
                .address(mapToAddress(userRequest.getAddress())) 
                .active(true)
                .build();

        User savedUser = userRepository.save(user);

        log.info("User registered with id: {}", savedUser.getId());

        return mapToResponse(savedUser);
    }

    
    private User.Address mapToAddress(UserRequest.AddressRequest req) {

        if (req == null) return null;

        return User.Address.builder()
                .street(req.getStreet())
                .city(req.getCity())
                .state(req.getState())
                .pincode(req.getPincode())
                .country(req.getCountry())
                .build();
    }

    private UserResponse.AddressResponse mapToAddressResponse(User.Address address) {

        if (address == null) return null;

        return UserResponse.AddressResponse.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .pincode(address.getPincode())
                .country(address.getCountry())
                .build();
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .address(mapToAddressResponse(user.getAddress()))
                .active(user.getActive())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }


	@Override
	public UserResponse getUserById(String id) {
		
		log.info("Fetching the user id: {} "+id);
		User user = userRepository.findById(id).orElseThrow(()-> new UserNotFoundException(id));
		return mapToResponse(user);
	}


	@Override
	public List<UserResponse> getAllUsers() {
		return userRepository.findByActiveTrue()
		.stream()
		.map(this::mapToResponse)
		.toList();
	}


	@Override
	public List<UserResponse> getAllUsersByEmail(String email) {
		return userRepository.findByEmail(email)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}


	@Override
	public List<UserResponse> getAllUsersByRole(UserRoles roles) {
		return userRepository.findByRole(roles)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}



	@Override
	public UserResponse updateUserDetails(String id, @Valid UserRequest userRequest) {
		
		
		User existingUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
		
		if (!existingUser.getEmail().equalsIgnoreCase(userRequest.getEmail())
                && userRepository.existsByEmail(userRequest.getEmail())) {
            throw new DuplicateEmailException(userRequest.getEmail());
        }
		
		
		User user = existingUser.builder()
			.firstName(userRequest.getFirstName())
			.lastName(userRequest.getLastName())
			.email(userRequest.getEmail())
			.phone(userRequest.getPhone())
			.address(mapToAddress(userRequest.getAddress()))
			.build();
		
		
		
		return mapToResponse(userRepository.save(user));
	}


	@Override
	public void deleteUser(String id) {
		log.warn("Soft-deleting user id: {} "+id);
		User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
		user.setActive(false);
		userRepository.save(user);
		
	}


	@Override
	public void deactiveUser(String id) {
		log.warn("Deactive user id: {} "+id);
		User user = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
		user.setActive(false);
		userRepository.save(user);
		
	}


	@Override
	public List<UserResponse> searchUsers(String name) {
		log.info("Searching users by name : {} "+name);
		
		return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}


	@Override
	public UserResponse updateUserRoleById(String id, UserRoles roleUpdateRequest) {
		log.info("Updating role for user: {} to: {}", id, roleUpdateRequest);
		User existingUser = userRepository.findById(id).orElseThrow(()->new UserNotFoundException(id));
		existingUser.setRole(roleUpdateRequest);
		
		return mapToResponse(userRepository.save(existingUser));
	}
	
}