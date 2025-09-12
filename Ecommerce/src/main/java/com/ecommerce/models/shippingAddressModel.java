package com.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Table(name = "shipping_address")
@Data
@NoArgsConstructor
public class shippingAddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Address information

    @Column(name = "address_line1", nullable = false, unique = true)
    @NotBlank(message = "Address line 1 is required")
    private String addressLine1;

    @Column(name = "address_line2")
    private String addressLine2;

    @Column(name = "city_municipality", nullable = false)
    @NotBlank(message = "City is required")
    private String city;

    @Column(name = "state_prov_region", length = 105)
    private String region;

    @Column(name = "postal_code", length = 25)
    private String postalCode;

    @Column(name = "Country", nullable = false, length = 105)
    @NotBlank(message = "Country is required")
    private String country;

    //Contact information

    @Column(name = "fullname", nullable = false, length = 155)
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Column(name = "phone", nullable = false, length = 25, unique = true)
    @NotBlank(message = "Phone number is required")
    private String phone;

    @Column(name = "id_client")
    private Long idClient;

    @OneToMany(mappedBy = "shippingAddress")
    @JsonIgnore
    private Set<invoicesModel> invoices;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public shippingAddressModel(Long id) {
        this.id = id;
    }

}
