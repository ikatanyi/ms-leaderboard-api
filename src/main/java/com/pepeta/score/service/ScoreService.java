package com.castille.pkg.service;

import com.castille.exception.APIException;
import com.castille.pkg.data.ProductPackageDto;
import com.castille.pkg.model.ProductPackage;
import com.castille.pkg.model.specification.ProductPackageSpecification;
import com.castille.pkg.repository.ProductPackageRepository;
import com.castille.product.model.Product;
import com.castille.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 *
 * @author Kennedy Ikatanyi
 */
@Slf4j
@Service
@RequiredArgsConstructor
//@CacheConfig(cacheNames = {"ProductPackage"})
public class ProductPackageService {

    private final ProductPackageRepository productPackageRepository;
    private final ProductService productService;

//
    @Transactional
    public ProductPackage createProductPackage(ProductPackageDto productPackageDto) {
        Product product = productService.fetchProductOrThrow(productPackageDto.getProductId());
        ProductPackage productPackage = productPackageDto.toProductPackage();
        productPackage.setProduct(product);
        return productPackageRepository.save(productPackage);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public ProductPackage updateItem(Long id, ProductPackageDto productPackageData) {
        ProductPackage productPackage = fetchProductPackageOrThrow(id);
        productPackage.setDescription(productPackageData.getDescription());
        return productPackageRepository.save(productPackage);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean deleteProductPackage(Long id) {
        ProductPackage productPackage = fetchProductPackageOrThrow(id);
        productPackageRepository.delete(productPackage);
        return true;
    }

    public Page<ProductPackage> fetchProductPackages(String productName, String description, Pageable pageable) {
        Specification<ProductPackage> spec = ProductPackageSpecification.createSpecification(productName, description);
        Page<ProductPackage> products = productPackageRepository.findAll(spec, pageable);
        return products;
    }

    public ProductPackage fetchProductPackageOrThrow(Long id) {
        return this.productPackageRepository.findById(id)
                .orElseThrow(() -> APIException.notFound("ProductPackage identified by id {0} not found.", id));
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Collection<ProductPackage> findAll() {
        return productPackageRepository.findAll();
    }


}
