package com.smartCommerce.smart_commerce.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartCommerce.smart_commerce.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

	Page<Product> findByCategory(String Category, Pageable pageable );
	
	Page<Product> findByActiveTrue(Pageable  pageable);
	
	Page<Product> findByNameContainingIgnoreCase(String name, Pageable pageable);
	
	List<Product> findByNameContainingIgnoreCase(String name);
	
	List<Product> findByCategoryAndActiveTrue(String Category);
	
	@Query("{ 'price': { $gte: ?0, $lte: ?1 }, 'active': true }")
    List<Product> findActiveProductsByPriceRange(BigDecimal min, BigDecimal max);
	
	boolean existsByNameIgnoreCase(String name);

	List<Product> findByActiveTrue();

}
