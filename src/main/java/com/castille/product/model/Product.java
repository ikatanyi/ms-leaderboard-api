package com.castille.product.model;

import com.castille.product.data.ProductDto;
import com.castille.pkg.model.ProductPackage;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "resources_inventory")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,property = "id")
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Schema(description = "The type of resources", example = "Internet/Tv/Telephone/Mobile")
    private String description;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductPackage>packages = new ArrayList<>();

    public void addPackages(List<ProductPackage> productPackages) {
        this.packages = productPackages;
        this.packages.forEach(x -> x.setProduct(this));
    }

    public void addPackage(ProductPackage productPackage) {
        productPackage.setProduct(this);
        this.packages.add(productPackage);
    }

    public ProductDto toProductDto(){
        ProductDto productDto = new ProductDto();
        productDto.setId(this.getId());
        productDto.setDescription(this.getDescription());
        //UNCOMMENT THIS LINE TO GET THE PACKAGES IN THE PRODUCT
        productDto.setProductPackages(this.packages.stream().map(ProductPackage::toProductPackageDto).collect(Collectors.toList()));
        return productDto;
    }
}
