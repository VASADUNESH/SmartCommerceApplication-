package com.smartCommerce.smart_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.smartCommerce.smart_commerce.dto.request.ProductRequest;
import com.smartCommerce.smart_commerce.dto.request.StockUpdateRequest;
import com.smartCommerce.smart_commerce.dto.response.ApiResponse;
import com.smartCommerce.smart_commerce.dto.response.PagedResponse;
import com.smartCommerce.smart_commerce.dto.response.ProductResponse;
import com.smartCommerce.smart_commerce.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
@Tag(name = "Products", description ="Product Management APIs ")
public class ProductController {

	
	private final ProductService productService;
	
	@PostMapping
	@Operation(summary = "Create a new product")
	public ResponseEntity<ApiResponse<ProductResponse>> createProduct (
			@Valid @RequestBody ProductRequest request) throws IllegalAccessException{
		ProductResponse productResponse =  productService.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(ApiResponse.created(productResponse));
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get product by Id")
	public ResponseEntity<ApiResponse<ProductResponse>> getProductById(
			 @Parameter(description = "Product id") @PathVariable String id
			){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.getProductById(id)));
	}
	
	@GetMapping
	@Operation(summary = "Get all products with pagination")
	public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getAllProducts(
			@RequestParam(defaultValue = "0")  int page,
	        @RequestParam(defaultValue = "10") int size,
	        @RequestParam(defaultValue = "createdAt") String sortBy,
	        @RequestParam(defaultValue = "desc") String sortDir
			){
		Sort sort = sortDir.equalsIgnoreCase("asc")
	            ? Sort.by(sortBy).ascending()
	            : Sort.by(sortBy).descending();
		
		Pageable pageable = PageRequest.of(page, size, sort);

		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.getAllProductsPaged(pageable)));
	}
	
	@GetMapping("/category/{category}")
	@Operation(summary = "Get product by category with pagination")
	public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> getByCategory(
			@Parameter(description = "Category") @PathVariable String category,
			@RequestParam(defaultValue = "0")  int page,
	        @RequestParam(defaultValue = "10") int size
			){
		
		Pageable pagable = PageRequest.of(page, size, Sort.by("createdAt").descending());
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.getProductsByCategoryPaged(category, pagable)));
	}
	
	@GetMapping("/search")
	@Operation(summary = "Search product by name with pagination")
	public ResponseEntity<ApiResponse<PagedResponse<ProductResponse>>> serachProduct(
			@Parameter(description = "Product name") @RequestParam String name ,
			@RequestParam(defaultValue = "0")  int page,
	        @RequestParam(defaultValue = "10") int size
			){
		Pageable pagable = PageRequest.of(page, size);
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.searchProductsPaged(name, pagable)));
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Update product")
	public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(
			@PathVariable String id, @Valid @RequestBody ProductRequest request
			){
		
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.updateProduct(id, request),"Product details updatad"));
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "delete product")
	public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable String id){
		productService.deleteProduct(id);
		return ResponseEntity.ok(ApiResponse.success(null, "Product details deleated"));
	}
	
	@PatchMapping("/{id}/stock")
	@Operation(summary = "Update product by stock")
	public ResponseEntity<ApiResponse<ProductResponse>> updateStock(
			@PathVariable String id,
			@RequestBody @Valid StockUpdateRequest stockUpdaterequest
			){
		return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(productService.updateStock(id, stockUpdaterequest), "Stock details updated"));
	}
	
	
}
