package com.castille.product.data;

import com.castille.pkg.data.ProductPackageDto;
import com.castille.product.model.Product;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ProductDto {
    @Schema(hidden = true)
    private Long id;
    private String description;
    private List<ProductPackageDto> productPackages=new ArrayList<>();

    public Product toProduct(){
        Product product = new Product();
        product.setDescription(this.getDescription());
        product.addPackages(this.productPackages.stream().map(ProductPackageDto::toProductPackage).collect(Collectors.toList()));
        return product;
    }
}
