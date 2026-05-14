package com.smartCommerce.smart_commerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartCommerce.smart_commerce.dto.request.ProductRequest;
import com.smartCommerce.smart_commerce.dto.request.StockUpdateRequest;
import com.smartCommerce.smart_commerce.dto.response.PagedResponse;
import com.smartCommerce.smart_commerce.dto.response.ProductResponse;
import com.smartCommerce.smart_commerce.exception.ProductNotFoundException;
import com.smartCommerce.smart_commerce.mapper.ProductMapper;
import com.smartCommerce.smart_commerce.model.Product;
import com.smartCommerce.smart_commerce.repository.ProductRepository;
import com.smartCommerce.smart_commerce.service.ProductService;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Data
public class ProductServiceImpl implements ProductService {

	
	private final ProductRepository productRepository;
	
	private final ProductMapper productMapper;

	@Override
	public ProductResponse createProduct(ProductRequest productRequest) throws IllegalAccessException {
		log.info("Creating new product with name: {}" + productRequest.getName());
		System.out.println("Creating new product with name");
		if (productRepository.existsByNameIgnoreCase(productRequest.getName())) {
			throw new IllegalAccessException("Product with name '" + productRequest.getName() + "' already exists");
		}

		Product product = productMapper.toEntity(productRequest);

	
		product.setActive(true);
		log.info("Product created successfully");
		
		return productMapper.toResponse(productRepository.save(product));
	}

	@Override
	public ProductResponse getProductById(String id) {
		
		log.debug("Fetching product by id: {}",id);
		
		Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
		
		return mapToResponse(product);
	}

	@Override
	public List<ProductResponse> getAllProducts() {
		log.debug("Fetching all products");
		return productRepository.findByActiveTrue()
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public List<ProductResponse> getProductsByCategory(String Category) {
		
		return productRepository.findByCategoryAndActiveTrue(Category)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public List<ProductResponse> searchProduct(String name) {
		return productRepository.findByNameContainingIgnoreCase(name)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public ProductResponse updateProduct(String id, ProductRequest productRequest) {
		Product existingProduct = productRepository.findById(id).orElseThrow(()-> new ProductNotFoundException(id));
		
		log.info("Updating product by id: {}"+ id);
		
		productMapper.updateResponse(productRequest, existingProduct);
		
		
		
		log.info("Product updated successfully");
		
		return productMapper.toResponse(productRepository.save(existingProduct));
	}

	@Override
	public void deleteProduct(String id) {
		log.warn("Soft-deleting product with id: {}",id);
		Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
		product.setActive(false);
		//productRepository.deleteById(id);
		productRepository.save(product);
		
		log.warn("Product soft-deleted: {}", id);

	}

	private ProductResponse mapToResponse(Product product) {
		return ProductResponse.builder().id(product.getId()).name(product.getName())
				.description(product.getDescription()).price(product.getPrice()).category(product.getCategory())
				.stockQuantity(product.getStockQuantity()).imageUrls(product.getImageUrls()).active(product.getActive())
				.createdAt(product.getCreatedAt()).updatedAt(product.getUpdatedAt()).build();
	}

	@Override
	public ProductResponse updateStock(String id, StockUpdateRequest stockUpdaterequest) {
		Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
		product.setStockQuantity(stockUpdaterequest.getQuantity());
		return mapToResponse(productRepository.save(product));
	}

	@Override
	public PagedResponse<ProductResponse> getAllProductsPaged(Pageable pageable) {
		log.debug("Fetching products page: {}, size: {}", pageable.getPageNumber(), pageable.getPageSize());
		
		Page<ProductResponse> page = productRepository.findByActiveTrue(pageable)
				.map(this::mapToResponse);
		
		return PagedResponse.from(page);
	}

	@Override
	public PagedResponse<ProductResponse> getProductsByCategoryPaged(String category, Pageable pageable) {
		Page<ProductResponse> page = productRepository.findByCategory(category,pageable)
		.map(this::mapToResponse);
		return PagedResponse.from(page);
	}

	@Override
	public PagedResponse<ProductResponse> searchProductsPaged(String name, Pageable pageable) {
		Page<ProductResponse> page = productRepository.findByNameContainingIgnoreCase(name, pageable)
		.map(this::mapToResponse);
		return PagedResponse.from(page);
	}

}
