package com.smartCommerce.smart_commerce.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.smartCommerce.smart_commerce.model.enums.UserRoles;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection  = "user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	private String id; 

	private String firstName; 

	private String lastName;

	@Indexed(unique = true)
	private String email;

	private String phone;

	private UserRoles role;

	private Address address;

	private Boolean active;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
	
	
	@Data
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class Address{
		private String street;

		private String city;

		private String state;

		private String pincode;

		private String country;
	}
}
