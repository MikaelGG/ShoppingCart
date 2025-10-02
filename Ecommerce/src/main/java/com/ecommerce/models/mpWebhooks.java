package com.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
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

    private Long userId;
    private String status;
    private String paymentType;
    private String paymentMethod;
    private BigDecimal amount;
    private String currency;

    @Column(columnDefinition = "json")
    private String rawData;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;
}
