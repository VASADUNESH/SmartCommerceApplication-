package com.smartCommerce.smart_commerce.dto.request;

import com.smartCommerce.smart_commerce.model.enums.UserRoles;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleUpdateRequest {
	@NotNull(message = "Role is required")
	@Schema(
            description = "User role",
            example = "ADMIN",
            allowableValues = {"CUSTOMER", "SELLER", "ADMIN"}
    )
	UserRoles role;
}
