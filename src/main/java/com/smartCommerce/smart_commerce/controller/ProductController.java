package com.smartCommerce.smart_commerce.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartCommerce.smart_commerce.dto.request.ProductRequest;
import com.smartCommerce.smart_commerce.dto.respone.ProductResponse;
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
	public ResponseEntity<ProductResponse> createProduct (
			@Valid @RequestBody ProductRequest request) throws IllegalAccessException{
		ProductResponse productResponse =  productService.createProduct(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(productResponse);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Get product by Id")
	public ResponseEntity<ProductResponse> getProductById(
			 @Parameter(description = "Product id") @PathVariable String id
			){
		return ResponseEntity.status(HttpStatus.OK).body(productService.getProductById(id));
	}
	
	@GetMapping
	@Operation(summary = "Get all products")
	public ResponseEntity<List<ProductResponse>> getAllProducts(){
		return ResponseEntity.status(HttpStatus.OK).body(productService.getAllProducts());
	}
	
	@GetMapping("/category/{category}")
	@Operation(summary = "Get product by category")
	public ResponseEntity<List<ProductResponse>> getByCategory(
			@Parameter(description = "Category") @PathVariable String category
			){
		return ResponseEntity.status(HttpStatus.OK).body(productService.getProductsByCategory(category));
	}
	
	@GetMapping("/search")
	@Operation(summary = "Search product by name")
	public ResponseEntity<List<ProductResponse>> serachProduct(
			@Parameter(description = "Product name") @RequestParam String name 
			){
		return ResponseEntity.status(HttpStatus.OK).body(productService.searchProduct(name));
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<ProductResponse> updateProduct(
			@PathVariable String id, @Valid @RequestBody ProductRequest request
			){
		
		return ResponseEntity.status(HttpStatus.OK).body(productService.updateProduct(id, request));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String id){
		productService.deleteProduct(id);
		return ResponseEntity.noContent().build();
	}
	
}
