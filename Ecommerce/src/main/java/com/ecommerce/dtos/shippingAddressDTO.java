package com.ecommerce.dtos;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.UniqueElements;

@Data
public class shippingAddressDTO {

    @UniqueElements(message = "Address line 1 must be unique")
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String city;

    private String region;

    private String postalCode;

    @NotBlank
    private String country;

    @NotBlank
    private String fullName;

    @UniqueElements(message = "Phone number must be unique")
    private String phone;

}
