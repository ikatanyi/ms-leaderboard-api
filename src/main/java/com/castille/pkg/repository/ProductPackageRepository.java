package com.castille.pkg.repository;

import com.castille.pkg.model.ProductPackage;
import com.castille.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductPackageRepository extends JpaRepository<ProductPackage, Long>, JpaSpecificationExecutor<ProductPackage> {

    List<ProductPackage> findByProduct(Product product);
}

