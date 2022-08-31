package com.castille.order.data;

import com.castille.order.model.Order;
import com.castille.pkg.data.ProductPackageDto;
import com.castille.product.model.Product;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = OrderDto.class)
public class OrderDto implements Serializable {
    @Schema(hidden = true)
    private Long id;
    @NotNull(message = "customer id is required")
    private Long customerId;
    @NotNull(message = "product id is required")
    private Long productId;
    @NotNull(message = "package id is required")
    private Long productPackageId;
    @NotNull(message = "package id is required")
    private LocalDateTime installationTime=LocalDateTime.now();
    @NotNull(message = "installation address required")
    private String installationAddress;
    @Schema(hidden = true)
    private String productName;
    @Schema(hidden = true)
    private String productPackage;
    @Schema(hidden = true)
    private String customerName;

    public Order toOrder(){
        Order order = new Order();
        order.setInstallationAddress(this.getInstallationAddress());
        order.setInstallationTime(this.getInstallationTime());
        return order;
    }
}
