package com.ecommerce.controllers;

import com.ecommerce.dtos.PreferenceDTO;
import com.ecommerce.models.mpWebhooks;
import com.ecommerce.services.MercadoPagoService;
import com.mercadopago.resources.preference.Preference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/mercadopago")
public class MercadoPagoRESTController {

    @Autowired
    MercadoPagoService mercadoPagoService;

    @PostMapping("/create-preference")
    public ResponseEntity<Map<String, String>> createPreference(@RequestBody List<PreferenceDTO> items) {
        try {
            Preference preference = mercadoPagoService.createPreference(items);

            Map<String, String> response = new HashMap<>();
            response.put("preferenceId", preference.getId());
            response.put("initPoint", preference.getInitPoint());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping("/webhooks")
    public ResponseEntity<mpWebhooks> webhooks(@RequestBody Map<String, Object> body) {
        return ResponseEntity.status(HttpStatus.OK).body(mercadoPagoService.WebHook(body));
    }

    @GetMapping("/webhooks")
    public ResponseEntity<List<mpWebhooks>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(mercadoPagoService.getAllWebhooks());
    }
}