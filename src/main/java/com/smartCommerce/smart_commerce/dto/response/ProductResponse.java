package com.smartCommerce.smart_commerce.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.LastModifiedDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {
	
	private String id;
	
	private String name;

	private String description;

	private BigDecimal price;

	private String category;

	private Integer stockQuantity;

	private List<String> imageUrls;

	private Boolean active;
	
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;
}
