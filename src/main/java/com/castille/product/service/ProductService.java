package com.castille.product.service;

import com.castille.exception.APIException;
import com.castille.product.data.ProductDto;
import com.castille.product.model.Product;
import com.castille.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
//@CacheConfig(cacheNames = {"Order"})
public class ProductService {

    private final ProductRepository resourceRepository;

//
    @Transactional
    public Product createProduct(ProductDto resourceDto) {
        Product resource = resourceDto.toProduct();
        return resourceRepository.save(resource);
    }
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Product updateItem(Long id, ProductDto resourceData) {
        Product resource = fetchProductOrThrow(id);
        resource.setDescription(resourceData.getDescription());
        return resourceRepository.save(resource);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public Boolean deleteProduct(Long id) {
        Product resource = fetchProductOrThrow(id);
        resourceRepository.delete(resource);
        return true;
    }

    public Page<Product> fetchProducts(Pageable pageable) {
        Page<Product> products = resourceRepository.findAll(pageable);
        return products;
    }

    public Product fetchProductOrThrow(Long id) {
        return this.resourceRepository.findById(id)
                .orElseThrow(() -> APIException.notFound("Order id {0} not found.", id));
    }

    @Transactional(readOnly = true)
    @Cacheable
    public Collection<Product> findAll() {
        return resourceRepository.findAll();
    }


}
