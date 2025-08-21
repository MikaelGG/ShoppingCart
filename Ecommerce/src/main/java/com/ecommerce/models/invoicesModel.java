package com.ecommerce.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "invoices")
@Data
@NoArgsConstructor
public class invoicesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String invoiceNumber;

    @Column(name = "invoice_date", nullable = false, columnDefinition = "TIMESTAMP")
    @NotNull(message = "Invoice date cannot be blank")
    @Future
    private LocalDateTime invoiceDate;

    @Column(name = "id_client")
    private Long idClient;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_shipping_address", nullable = false)
    private shippingAddressModel shippingAddress;

    @Column(name = "subtotal", nullable = false)
    @NotNull(message = "Subtotal cannot be blank")
    private Double subtotal;

    @Column(name = "discount", length = 10)
    private Short discount;

    @Column(name = "tax", length = 10)
    private Short tax;

    @Column(name = "total", nullable = false)
    @NotNull(message = "Total cannot be blank")
    private Double total;

    @OneToMany(mappedBy = "idInvoice", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<invoiceProductsModel> invoiceProducts;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public invoicesModel(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

}
