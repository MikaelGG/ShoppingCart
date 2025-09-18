package com.products.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "product_info")
@Data
public class productInfoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    @Column(name = "photo", nullable = false, length = 255)
    @NotNull(message = "Photo URL cannot be null")
    private String photo;

    @Column(name = "name", nullable = false, length = 125)
    private String name;

    @Column(name = "description", nullable = false, length = 255)
    @Size(min = 50, max = 255, message = "Description must be between 50 and 255 characters")
    private String description;

    @Column(name = "quantity", nullable = false)
    private Long quantity;

    @Column(name = "price", nullable = false)
    private Double price;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_product_type", nullable = false, unique = true)
    private productTypeModel productType;


}
