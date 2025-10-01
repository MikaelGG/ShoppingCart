package com.ecommerce.services;

import com.ecommerce.dtos.PreferenceDTO;
import com.ecommerce.models.mpWebhooks;
import com.ecommerce.repositories.mpWebhooksRepository;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.resources.preference.Preference;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class MercadoPagoService {

    @Value("${ACCESS_TOKEN}")
    private String accessToken;

    @Autowired
    mpWebhooksRepository mpWebhooksRepository;

    @Autowired
    RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        MercadoPagoConfig.setAccessToken(accessToken);
    }

    @Transactional
    public Preference createPreference(List<PreferenceDTO> itemsData) throws Exception {
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
                .success("http://localhost:3000/purchase-records")
                .failure("http://localhost:3000/purchase-records")
                .pending("http://localhost:3000/purchase-records")
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrlsRequest)
                .notificationUrl("https://katharine-vomerine-sophia.ngrok-free.dev/mercadopago/webhooks")
                .build();

        PreferenceClient client = new PreferenceClient();
        Preference preference = client.create(preferenceRequest);

        return preference;
    }

    @Transactional
    public mpWebhooks WebHook(Map<String, Object> body) {
        try {
            System.out.println("📩 WebHook recibido: " + body);

            Map<String, Object> data = (Map<String, Object>) body.get("data");
            String paymentId = data.get("id").toString();

            String url = "https://api.mercadopago.com/v1/payments/" + paymentId;

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<Void> entity = new HttpEntity<>(headers);
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);

            Map pago = response.getBody();
            System.out.println("✅ Pago consultado: " + pago);

            mpWebhooks webhook = new mpWebhooks();
            webhook.setDataId(paymentId);
            webhook.setAction((String) body.get("action"));
            webhook.setType((String) body.get("type"));

            if (pago != null) {
                webhook.setStatus((String) pago.get("status"));
                webhook.setCurrency((String) pago.get("currency_id"));

                Object amount = pago.get("transaction_amount");
                if (amount != null) {
                    if (amount instanceof Number) {
                        webhook.setAmount(BigDecimal.valueOf(((Number) amount).doubleValue()));
                    } else {
                        webhook.setAmount(new BigDecimal(amount.toString()));
                    }
                }

                webhook.setPayload(pago.toString());
            }

            webhook.setProcessed(false);

            return mpWebhooksRepository.save(webhook);

        } catch (Exception e) {
            throw new RuntimeException("Error procesando Webhook", e);
        }
    }

    @Transactional(readOnly = true)
    public List<mpWebhooks> getAllWebhooks() {
        return mpWebhooksRepository.findAll();
    }
}