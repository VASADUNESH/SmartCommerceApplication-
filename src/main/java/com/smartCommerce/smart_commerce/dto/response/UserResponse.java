package com.smartCommerce.smart_commerce.dto.response;

import java.time.LocalDateTime;

import com.smartCommerce.smart_commerce.model.enums.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserResponse {
	
	private String id;
	
	private String firstName; 

	private String lastName;

	private String email;

	private String phone;

	private UserRoles role;

	private AddressResponse  address;

	private Boolean active;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class AddressResponse {
		private String street;

		private String city;

		private String state;

		private String pincode;

		private String country;
	}
}
