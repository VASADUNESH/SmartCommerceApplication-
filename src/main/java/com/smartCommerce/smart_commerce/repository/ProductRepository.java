package com.smartCommerce.smart_commerce.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.smartCommerce.smart_commerce.model.Product;

@Repository
public interface ProductRepository extends MongoRepository<Product, String>{

	List<Product> findByCategory();
	
	List<Product> findByActiveTrue();
	
	List<Product> findByNameContainingIgnoreCase(String name);
	
	List<Product> findByCategoryAndActiveTrue(String Category);
	
	@Query("{ 'price': { $gte: ?0, $lte: ?1 }, 'active': true }")
    List<Product> findActiveProductsByPriceRange(BigDecimal min, BigDecimal max);
	
	boolean existsByNameIgnoreCase(String name);

}
