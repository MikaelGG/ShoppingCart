package com.ecommerce.services;

import com.ecommerce.models.mpWebhooks;
import com.ecommerce.models.purchaseModel;
import com.ecommerce.repositories.mpWebhooksRepository;
import com.ecommerce.repositories.purchaseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class purchaseService {

    @Autowired
    purchaseRepository purchaseRepository;

    @Autowired
    mpWebhooksRepository mpWebhooksRepository;

    @Autowired
    RestTemplate restTemplate;

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

    @Transactional(readOnly = true)
    public List<purchaseModel> getAllPurchase() {
        return purchaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<purchaseModel> getUserPurchases(String email) {
        return purchaseRepository.findByBuyerEmail(email);
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getPurchaseItems(String mpPaymentId) {
        mpWebhooks webhook = mpWebhooksRepository.findTopByMpIdOrderByCreatedAtDesc(mpPaymentId);

        if (webhook == null || webhook.getRawData() == null) {
            return Collections.emptyList();
        }

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode json = mapper.readTree(webhook.getRawData());
            JsonNode itemsNode = null;

            // Caso 1: items en rawData.additionalInfo.items
            if (json.has("additionalInfo") && json.get("additionalInfo").has("items")) {
                itemsNode = json.get("additionalInfo").get("items");
                System.out.println("✅ Items encontrados en rawData.additionalInfo.items");
            }

            // Caso 2: items dentro de response.content.additional_info.items
            else if (json.has("response") && json.get("response").has("content")) {
                String content = json.get("response").get("content").asText();
                JsonNode innerJson = mapper.readTree(content);

                if (innerJson.has("additional_info") && innerJson.get("additional_info").has("items")) {
                    itemsNode = innerJson.get("additional_info").get("items");
                    System.out.println("✅ Items encontrados en response.content.additional_info.items");
                }
            }

            // Caso 3: no había items -> consultamos a la API de MP
            else if (json.has("data") && json.get("data").has("id")) {
                String paymentId = json.get("data").get("id").asText();
                String url = "https://api.mercadopago.com/v1/payments/" + paymentId;

                HttpHeaders headers = new HttpHeaders();
                headers.setBearerAuth(accessToken);
                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
                JsonNode paymentJson = mapper.readTree(response.getBody());

                if (paymentJson.has("additional_info") && paymentJson.get("additional_info").has("items")) {
                    itemsNode = paymentJson.get("additional_info").get("items");
                    System.out.println("✅ Items encontrados desde la API de MercadoPago");
                }
            }

            if (itemsNode != null && itemsNode.isArray()) {
                return mapper.convertValue(itemsNode, new TypeReference<List<Map<String, Object>>>() {});
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }



    @Transactional
    public purchaseModel updateShippingStatus(Long id, purchaseModel.ShippingStatus status) {
        purchaseModel purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        purchase.setShippingStatus(status);

        return purchaseRepository.save(purchase);
    }
}
