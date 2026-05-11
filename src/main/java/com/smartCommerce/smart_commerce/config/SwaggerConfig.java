package com.smartCommerce.smart_commerce.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

public class SwaggerConfig {

	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("Smart Commerce API")
						.description("Production-grade e-commerce backend")
						.version("v1.0.0")
						.contact(new Contact()
								.name("Dunesh")
								.email("duneshvasa@gmail.com")
								)
						
						);
	}
}
