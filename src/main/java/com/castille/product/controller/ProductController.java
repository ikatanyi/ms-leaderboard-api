package com.castille.product.controller;

import com.castille.product.data.ProductDto;
import com.castille.product.model.Product;
import com.castille.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * @author Kelsas
 */
@RestController
@Slf4j
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;


    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
        ProductDto dto = service.createProduct(productDto).toProductDto();
        return ResponseEntity.ok(dto);

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(value = "id") Long id) {
        ProductDto dto = service.fetchProductOrThrow(id).toProductDto();
        return ResponseEntity.ok(dto);
    }
    

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductDto productDto) {

        Product product = service.updateItem(id, productDto);
        return ResponseEntity.ok(product.toProductDto());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") Long id) {

        Boolean isDeleted = service.deleteProduct(id);
        return ResponseEntity.ok(isDeleted);

    }

    @GetMapping
    public ResponseEntity<?> getAllProducts(
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20",required = false) Integer size) {
        page = page>=1 ? page-1 : page;
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductDto> pagedList = service.fetchProducts(pageable).map(u -> u.toProductDto());
        return ResponseEntity.ok(pagedList);
    }

}
