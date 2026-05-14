package com.smartCommerce.smart_commerce.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.smartCommerce.smart_commerce.dto.request.ProductRequest;
import com.smartCommerce.smart_commerce.dto.request.StockUpdateRequest;
import com.smartCommerce.smart_commerce.dto.response.PagedResponse;
import com.smartCommerce.smart_commerce.dto.response.ProductResponse;

public interface ProductService {

	ProductResponse createProduct(ProductRequest productRequest) throws IllegalAccessException;
	ProductResponse getProductById(String id);
	List<ProductResponse> getAllProducts();
	List<ProductResponse> getProductsByCategory(String Category);
	List<ProductResponse> searchProduct(String name);
	ProductResponse updateProduct(String id, ProductRequest productRequest);
	void deleteProduct(String id);
	ProductResponse updateStock(String id, StockUpdateRequest stockUpdaterequest);
	PagedResponse<ProductResponse> getAllProductsPaged(Pageable pageable);
	PagedResponse<ProductResponse> getProductsByCategoryPaged(String category, Pageable pageable);
	PagedResponse<ProductResponse> searchProductsPaged(String name, Pageable pageable);
}
