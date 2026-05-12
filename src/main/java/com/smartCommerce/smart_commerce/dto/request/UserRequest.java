package com.smartCommerce.smart_commerce.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequest {

    @NotBlank(message = "First name is required")
    @Size(min = 2, max = 50, message = "First name must be 2-50 characters")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(min = 2, max = 50, message = "Last name must be 2-50 characters")
    private String lastName;

    @NotBlank(message = "email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 characters")
    private String phone;

    @Valid
    private AddressRequest address;  

    @Data
    public static class AddressRequest {

        private String street;

        private String city;

        @Size(max = 50)
        private String state;

        @Pattern(regexp = "^[0-9]{6}$", message = "Pincode must be 6 digits")
        private String pincode;

        private String country;
    }
}