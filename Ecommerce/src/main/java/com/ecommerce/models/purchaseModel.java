package com.ecommerce.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;

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
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ShippingStatus shippingStatus = ShippingStatus.EN_PROCESO;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    public enum ShippingStatus {
        EN_PROCESO,
        ENVIADO,
        ENTREGADO,
        PROBLEMA
    }
}

