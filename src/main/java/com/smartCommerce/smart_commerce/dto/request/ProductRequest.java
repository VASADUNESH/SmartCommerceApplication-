package com.smartCommerce.smart_commerce.dto.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductRequest {
	private String name;

	private String description;

	private BigDecimal price;

	private String category;

	private Integer stockQuantity;

	private List<String> imageUrls;
	
	private Boolean active;

}
