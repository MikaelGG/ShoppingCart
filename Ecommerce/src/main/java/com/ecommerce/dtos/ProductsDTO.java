package com.ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductsDTO {
    private ProductDTO idProduct;
    private Integer quantity;
    private Double unitPrice;
    private Double totalProduct;
}
