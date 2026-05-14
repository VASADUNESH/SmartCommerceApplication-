package com.smartCommerce.smart_commerce.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {

	private String token;
    private String type;       
    private String id;
    private String email;
    private String role;
    private long expiresIn;
}
