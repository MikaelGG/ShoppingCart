package com.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "mp_webhooks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class mpWebhooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String topic;

    @Column(name = "mp_id")
    private String mpId;

    private String status;
    private String paymentType;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;

    @Column(columnDefinition = "json")
    private String rawData;

    @Column(name = "created_at", insertable = false, updatable = false)
    private LocalDateTime createdAt;
}
