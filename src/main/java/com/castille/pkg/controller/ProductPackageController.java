package com.castille.pkg.controller;

import com.castille.pkg.data.ProductPackageDto;
import com.castille.pkg.model.ProductPackage;
import com.castille.pkg.service.ProductPackageService;
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
 * @author Kennedy Ikatanyi
 */
@RestController
@Slf4j
@RequestMapping("/api/productPackage")
@RequiredArgsConstructor
public class ProductPackageController {

    private final ProductPackageService service;


    @PostMapping
    public ResponseEntity<?> createProductPackage(@Valid @RequestBody ProductPackageDto productPackageDto) {
        ProductPackageDto dto = service.createProductPackage(productPackageDto).toProductPackageDto();
        return ResponseEntity.ok(dto);

    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getProductPackageById(@PathVariable(value = "id") Long id) {
        ProductPackageDto dto = service.fetchProductPackageOrThrow(id).toProductPackageDto();
        return ResponseEntity.ok(dto);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateProductPackage(@PathVariable(value = "id") Long id, @Valid @RequestBody ProductPackageDto productPackageDto) {

        ProductPackage productPackage = service.updateItem(id, productPackageDto);
        return ResponseEntity.ok(productPackage.toProductPackageDto());

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProductPackage(@PathVariable(value = "id") Long id) {

        Boolean isDeleted = service.deleteProductPackage(id);
        return ResponseEntity.ok(isDeleted);

    }

    @GetMapping
    public ResponseEntity<?> getAllProductPackages(
            @RequestParam(value = "description",required = false) String description,
            @RequestParam(value = "productName",required = false) String productName,
            @RequestParam(value = "page", defaultValue = "0",required = false) Integer page,
            @RequestParam(value = "pageSize", defaultValue = "20",required = false) Integer size) {
        page = page>=1 ? page-1 : page;
        Pageable pageable = PageRequest.of(page, size);

        Page<ProductPackageDto> pagedList = service.fetchProductPackages(productName, description, pageable).map(u -> u.toProductPackageDto());
        return ResponseEntity.ok(pagedList);
    }

}
