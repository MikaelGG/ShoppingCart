package com.ecommerce.models;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "mp_webhooks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class mpWebhooks {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;
    private String dataId;
    private String type;
    private String status;
    private BigDecimal amount;
    private String currency;

    @Column(columnDefinition = "json")
    private String payload;

    private Boolean processed = false;

    @Column(name = "received_at", insertable = false, updatable = false)
    private java.sql.Timestamp receivedAt;
}
