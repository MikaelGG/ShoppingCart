package com.ecommerce.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {

    private String code;
    private String photo;
    private String name;
    private String description;
    private Double price;
    private ProductType productType;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductType {
        private Long id;
        private String nameType;
    }

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public ProductDTO(String code) {
        this.code = code;
    }

}
