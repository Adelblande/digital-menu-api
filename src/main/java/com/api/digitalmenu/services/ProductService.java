package com.api.digitalmenu.services;

import com.api.digitalmenu.models.ProductModel;
import com.api.digitalmenu.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    @Transactional
    public ProductModel save(ProductModel productModel) {
        return productRepository.save(productModel);
    }

    public Page<ProductModel> getAll(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    public Optional<ProductModel> getById(UUID id) {
        return productRepository.findById(id);
    }

    @Transactional
    public void remove(ProductModel productModel) {
        productRepository.delete(productModel);
    }
}
