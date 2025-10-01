package com.ecommerce.dtos;

import lombok.Data;

@Data
public class PreferenceDTO {

    private String code;
    private String name;
    private String photo;
    private Long quantity;
    private Double price;

}
