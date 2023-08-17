package com.api.digitalmenu.controllers;

import com.api.digitalmenu.dtos.ProductRecordDto;
import com.api.digitalmenu.models.ProductModel;
import com.api.digitalmenu.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
    @Autowired
    ProductService productService;

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid ProductRecordDto productRecordDto) {
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        productModel.setCreated_date(LocalDateTime.now(ZoneId.of("UTC")));
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(productModel));
    }

    @GetMapping
    public ResponseEntity<Page<ProductModel>> getAll(
            @PageableDefault(page = 0, size = 10, sort = "name", direction = Sort.Direction.ASC) Pageable pageable
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOne(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productService.getById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(product.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<ProductModel> product = productService.getById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        productService.remove(product.get());
        return ResponseEntity.status(HttpStatus.OK).body("Product deleted with success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") UUID id,
            @RequestBody @Valid ProductRecordDto productRecordDto
    ) {
        Optional<ProductModel> product = productService.getById(id);
        if (product.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found");
        }
        var productModel = new ProductModel();
        BeanUtils.copyProperties(productRecordDto, productModel);
        productModel.setId(product.get().getId());
        productModel.setCreated_date(product.get().getCreated_date());

        return ResponseEntity.status(HttpStatus.OK).body(productService.save(productModel));
    }

}
