package com.smartCommerce.smart_commerce.service;

import java.util.List;

import com.smartCommerce.smart_commerce.dto.request.ProductRequest;
import com.smartCommerce.smart_commerce.dto.respone.ProductResponse;

public interface ProductService {

	ProductResponse createProduct(ProductRequest productRequest) throws IllegalAccessException;
	ProductResponse getProductById(String id);
	List<ProductResponse> getAllProducts();
	List<ProductResponse> getProductsByCategory(String Category);
	List<ProductResponse> searchProduct(String name);
	ProductResponse updateProduct(String id, ProductRequest productRequest);
	void deleteProduct(String id);
}
