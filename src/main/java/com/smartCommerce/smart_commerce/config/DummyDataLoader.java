package com.smartCommerce.smart_commerce.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smartCommerce.smart_commerce.model.Product;
import com.smartCommerce.smart_commerce.model.User;
import com.smartCommerce.smart_commerce.model.User.Address;
import com.smartCommerce.smart_commerce.model.enums.UserRoles;
import com.smartCommerce.smart_commerce.repository.ProductRepository;
import com.smartCommerce.smart_commerce.repository.UserRepository;

//@Configuration
public class DummyDataLoader {

    private static final Random random = new Random();

    @Bean
    CommandLineRunner loadDummyData(
            UserRepository userRepository,
            ProductRepository productRepository) {

        return args -> {

            // Prevent duplicate insertion
            if (userRepository.count() > 0 || productRepository.count() > 0) {
                System.out.println("Dummy data already exists.");
                return;
            }

            // =========================
            // USERS
            // =========================

            List<User> users = new ArrayList<>();

            String[] firstNames = {
                    "John", "Jane", "Alex", "Michael", "David",
                    "Chris", "Emma", "Sophia", "Olivia", "Daniel"
            };

            String[] lastNames = {
                    "Smith", "Johnson", "Williams", "Brown",
                    "Jones", "Garcia", "Miller", "Davis"
            };

            String[] cities = {
                    "Hyderabad", "Bangalore", "Chennai",
                    "Mumbai", "Delhi", "Pune"
            };

            for (int i = 1; i <= 600; i++) {

                String firstName = firstNames[random.nextInt(firstNames.length)];
                String lastName = lastNames[random.nextInt(lastNames.length)];

                User user = User.builder()
                        .firstName(firstName)
                        .lastName(lastName)
                        .email("user" + i + "@gmail.com")
                        .phone(generatePhone())
                        .role(i % 5 == 0 ? UserRoles.ADMIN : UserRoles.CUSTOMER)
                        .active(true)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .address(Address.builder()
                                .street("Street " + i)
                                .city(cities[random.nextInt(cities.length)])
                                .state("State " + i)
                                .pincode(generatePincode())
                                .country("India")
                                .build())
                        .build();

                users.add(user);
            }

            userRepository.saveAll(users);

            // =========================
            // PRODUCTS
            // =========================

            List<Product> products = new ArrayList<>();

            String[] categories = {
                    "Electronics",
                    "Fashion",
                    "Books",
                    "Home",
                    "Sports",
                    "Beauty"
            };

            for (int i = 1; i <= 650; i++) {

                String category = categories[random.nextInt(categories.length)];

                Product product = Product.builder()
                        .name(category + " Product " + i)
                        .description("Description for product " + i)
                        .price(BigDecimal.valueOf(100 + random.nextInt(50000)))
                        .category(category)
                        .stockQuantity(random.nextInt(500))
                        .active(true)
                        .imageUrls(List.of(
                                "https://dummyimage.com/300x300/000/fff&text=Product+" + i,
                                "https://dummyimage.com/300x300/111/fff&text=Item+" + i
                        ))
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();

                products.add(product);
            }

            productRepository.saveAll(products);

            System.out.println("500+ Dummy Users & Products Inserted Successfully");
        };
    }

    // =========================
    // Helper Methods
    // =========================

    private static String generatePhone() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 10; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }

    private static String generatePincode() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(random.nextInt(10));
        }

        return sb.toString();
    }
}