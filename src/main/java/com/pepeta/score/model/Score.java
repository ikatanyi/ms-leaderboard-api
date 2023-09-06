package com.castille.pkg.model;

import com.castille.pkg.data.ProductPackageDto;
import com.castille.product.model.Product;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name = "product_package")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class ProductPackage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    private String description;

    public ProductPackageDto toProductPackageDto(){
        ProductPackageDto productPackageDto = new ProductPackageDto();
        productPackageDto.setId(this.getId());
        productPackageDto.setProductId(this.getProduct().getId());
        productPackageDto.setProductName(this.getProduct().getDescription());
        productPackageDto.setDescription(this.getDescription());
        return productPackageDto;
    }
}
