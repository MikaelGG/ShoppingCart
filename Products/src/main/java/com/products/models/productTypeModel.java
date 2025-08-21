package com.products.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "product_type")
@Data
@NoArgsConstructor
public class productTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_type", nullable = false, length = 105)
    @Size(min = 10, max = 105, message = "Name type must be between 10 and 105 characters")
    private String nameType;

    @OneToMany(mappedBy = "productType", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<productInfoModel> productInfoModels;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public productTypeModel(Long id) {
        this.id = id;
    }

}
