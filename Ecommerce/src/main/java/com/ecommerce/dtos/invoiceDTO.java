package com.ecommerce.dtos;

import com.ecommerce.models.shippingAddressModel;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class invoiceDTO {

    @NotNull(message = "Invoice date cannot be blank")
    @Future
    private LocalDateTime invoiceDate;
    private ClientDTO idClient;
    private shippingAddressModel shippingAddress;
    private List<ProductsDTO> products = new ArrayList<>();
    @NotNull(message = "Subtotal cannot be blank")
    private Double subtotal;
    private Short discount;
    private Short tax;
    @NotNull(message = "Total cannot be blank")
    private Double total;

}
