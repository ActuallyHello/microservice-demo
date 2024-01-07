package com.happyfxmas.productservice.service;

import com.happyfxmas.productservice.domain.Product;
import com.happyfxmas.productservice.dto.ProductRequest;
import com.happyfxmas.productservice.dto.ProductResponse;
import com.happyfxmas.productservice.repo.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ProductService {

    private final ProductRepo productRepo;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();
        productRepo.insert(product);
        log.info("Product {} is saved", product);
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepo.findAll();
        return products.stream()
                .map(product -> ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .build())
                .toList();
    }
}
