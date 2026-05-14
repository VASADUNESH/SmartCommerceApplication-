package com.smartCommerce.smart_commerce.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;
	
	@NotBlank @Email 
	private String email;
	
	@NotBlank
	@Size(min=8, message = "Password must contain  minmum 8 characters")
	private String password;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Password must be 10 characters")
	private String phone;
	
}
