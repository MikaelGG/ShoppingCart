package com.ecommerce.services;

import com.ecommerce.dtos.PreferenceDTO;
import com.ecommerce.models.mpWebhooks;
import com.ecommerce.models.purchaseModel;
import com.ecommerce.repositories.mpWebhooksRepository;
import com.ecommerce.repositories.purchaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.merchantorder.MerchantOrderClient;
import com.mercadopago.client.payment.PaymentClient;
import com.mercadopago.client.preference.*;
import com.mercadopago.resources.merchantorder.MerchantOrder;
import com.mercadopago.resources.payment.Payment;
import com.mercadopago.resources.preference.Preference;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.*;

@Service
@Slf4j
public class MercadoPagoService {

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

    @Autowired
    mpWebhooksRepository mpWebhooksRepository;

    @Autowired
    purchaseRepository purchaseRepository;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    @Transactional
    public Preference createPreference(List<PreferenceDTO> itemsData, Long userId) throws Exception {
        List<PreferenceItemRequest> items = new ArrayList<>();

        for (PreferenceDTO item : itemsData) {
            PreferenceItemRequest preferenceItem = PreferenceItemRequest.builder()
                    .id(item.getCode())
                    .title(item.getName())
                    .quantity(item.getQuantity().intValue())
                    .unitPrice(new BigDecimal(item.getPrice()))
                    .pictureUrl(item.getPhoto())
                    .currencyId("COP")
                    .build();
            items.add(preferenceItem);
        }

        PreferenceBackUrlsRequest backUrlsRequest = PreferenceBackUrlsRequest.builder()
                .success("https://wheat-workstation-decisions-que.trycloudflare.com/purchase-records")
                .failure("https://wheat-workstation-decisions-que.trycloudflare.com/purchase-records")
                .pending("https://wheat-workstation-decisions-que.trycloudflare.com/purchase-records")
                .build();

        PreferencePaymentMethodsRequest paymentMethods = PreferencePaymentMethodsRequest.builder().defaultInstallments(1).build();


        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrlsRequest)
                .notificationUrl("https://katharine-vomerine-sophia.ngrok-free.dev/mercadopago/webhooks")
                .paymentMethods(paymentMethods)
                .externalReference(userId.toString())
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return preference;
    }

    public void handleWebhook(Map<String, Object> request) {
        try {
            String topic = (String) request.get("topic");

            if (request.containsKey("data")) {
                Map<String, Object> data = (Map<String, Object>) request.get("data");
                String id = (String) data.get("id");
                processNotification(topic, id);

            } else if (request.containsKey("resource")) {
                String resource = (String) request.get("resource");
                String id = resource.substring(resource.lastIndexOf("/") + 1);
                processNotification(topic, id);

            } else {
                log.warn("⚠️ Webhook recibido con formato desconocido: {}", request);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error procesando Webhook", e);
        }
    }

    private void processNotification(String topic, String id) {
        switch (topic) {
            case "payment":
                log.info("💳 Procesando pago id={}", id);
                handlePayment(id);
                break;
            case "merchant_order":
                log.info("🛒 Procesando orden id={}", id);
                handleMerchantOrder(id);
                break;
            default:
                log.warn("⚠️ Topic no manejado: {}", topic);
        }
    }

    private void handlePayment(String paymentId) {
        try {
            PaymentClient client = new PaymentClient();
            Payment payment = client.get(Long.parseLong(paymentId));

            log.info("✅ Pago consultado: id={}, status={}", payment.getId(), payment.getStatus());

            mpWebhooks entity = new mpWebhooks();
            entity.setTopic("payment");
            entity.setUserId(Long.parseLong(payment.getExternalReference()));
            entity.setMpId(payment.getId().toString());
            entity.setStatus(Objects.toString(payment.getStatus(), "UNKNOWN"));
            entity.setPaymentType(Objects.toString(payment.getPaymentTypeId(), "UNKNOWN"));
            entity.setPaymentMethod(Objects.toString(payment.getPaymentMethodId(), "UNKNOWN"));
            entity.setAmount(payment.getTransactionAmount());
            entity.setCurrency(Objects.toString(payment.getCurrencyId(), "UNKNOWN"));
            String json = objectMapper.writeValueAsString(payment);
            entity.setRawData(json);

            mpWebhooksRepository.save(entity);

            purchaseModel purchase = purchaseRepository.findByMpPaymentId(payment.getId().toString()).orElse(new purchaseModel());
            purchase.setMpPaymentId(payment.getId().toString());
            purchase.setAmount(payment.getTransactionAmount());
            purchase.setCurrency(payment.getCurrencyId());
            purchase.setStatus(payment.getStatus());
            purchase.setUserId(Long.parseLong(payment.getExternalReference()));

            purchaseRepository.save(purchase);

        } catch (Exception e) {
            log.error("❌ Error consultando pago {}", paymentId, e);
        }
    }

    private void handleMerchantOrder(String orderId) {
        try {
            MerchantOrderClient client = new MerchantOrderClient();
            MerchantOrder order = client.get(Long.parseLong(orderId));

            log.info("✅ Orden consultada: id={}, status={}", order.getId(), order.getOrderStatus());

            mpWebhooks entity = new mpWebhooks();
            entity.setTopic("merchant_order");
            entity.setUserId(Long.parseLong(order.getExternalReference()));
            entity.setMpId(order.getId().toString());
            entity.setStatus(Objects.toString(order.getStatus(), "UNKNOWN"));
            entity.setAmount(order.getTotalAmount());
            entity.setCurrency("COP");
            String json = objectMapper.writeValueAsString(order);
            entity.setRawData(json);

            mpWebhooksRepository.save(entity);

        } catch (Exception e) {
            log.error("❌ Error consultando orden {}", orderId, e);
        }
    }

    @Transactional(readOnly = true)
    public List<mpWebhooks> getAllWebhooks() {
        return mpWebhooksRepository.findAll();
    }
}