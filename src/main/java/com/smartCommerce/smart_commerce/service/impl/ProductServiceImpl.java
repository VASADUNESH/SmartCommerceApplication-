package com.smartCommerce.smart_commerce.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.smartCommerce.smart_commerce.dto.request.ProductRequest;
import com.smartCommerce.smart_commerce.dto.request.StockUpdateRequest;
import com.smartCommerce.smart_commerce.dto.response.ProductResponse;
import com.smartCommerce.smart_commerce.exception.ProductNotFoundException;
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

	@Override
	public ProductResponse createProduct(ProductRequest productRequest) throws IllegalAccessException {
		log.info("Creating new product with name: {}" + productRequest.getName());
		System.out.println("Creating new product with name");
		if (productRepository.existsByNameIgnoreCase(productRequest.getName())) {
			throw new IllegalAccessException("Product with name '" + productRequest.getName() + "' already exists");
		}

		Product product = Product.builder().name(productRequest.getName()).description(productRequest.getDescription())
				.price(productRequest.getPrice()).category(productRequest.getCategory())
				.stockQuantity(productRequest.getStockQuantity()).imageUrls(productRequest.getImageUrls())
				.active(productRequest.getActive()).build();

		Product savedProduct = productRepository.save(product);
		log.info("Product created successfully with id: {}", savedProduct.getId());
		System.out.println("DOne service");
		return mapToResponse(savedProduct);
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
		
		Product updatedProduct = existingProduct.builder()
				.name(productRequest.getName())
				.description(productRequest.getDescription())
				.price(productRequest.getPrice())
				.category(productRequest.getCategory())
				.stockQuantity(productRequest.getStockQuantity())
				.imageUrls(productRequest.getImageUrls())
				.active(productRequest.getActive())
				.build();
		
		Product savedProduct = productRepository.save(updatedProduct);
		
		log.info("Product updated successfully:{}"+id);
		
		return mapToResponse(savedProduct);
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

}
