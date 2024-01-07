package com.happyfxmas.productservice.repo;

import com.happyfxmas.productservice.domain.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepo extends MongoRepository<Product, String> {
}
