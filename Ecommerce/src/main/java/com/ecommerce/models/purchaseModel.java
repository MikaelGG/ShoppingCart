package com.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class purchaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mpPaymentId; // el ID de pago de MP
    private String buyerEmail;  // cliente (puedes mapearlo con tu tabla de usuarios)
    private BigDecimal amount;
    private String currency;
    private String status; // PENDING, APPROVED, REJECTED

    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus = ShippingStatus.EN_PROCESO;

    @Column(name = "created_at", updatable = false, insertable = false)
    private java.sql.Timestamp createdAt;

    public enum ShippingStatus {
        EN_PROCESO,
        ENVIADO,
        ENTREGADO,
        PROBLEMA
    }
}

