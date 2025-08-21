package com.ecommerce.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "invoice_products")
@Data
public class invoiceProductsModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_invoice", nullable = false)
    private invoicesModel idInvoice;

    @Column(name = "id_product", nullable = false)
    @NotBlank(message = "Product ID must be provided")
    private String idProduct;

    @Column(name = "quantity", nullable = false)
    @NotNull(message = "Quantity must be provided")
    private Integer quantity;

    @Column(name = "unit_price", nullable = false)
    @NotNull(message = "Unit price must be provided")
    private Double unitPrice;

    @Column(name = "total_product", nullable = false)
    @NotNull(message = "Total product price must be provided")
    private Double totalProduct;

}
