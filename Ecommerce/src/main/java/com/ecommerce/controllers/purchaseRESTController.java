package com.ecommerce.controllers;

import com.ecommerce.models.purchaseModel;
import com.ecommerce.services.purchaseService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/purchases")
@RequiredArgsConstructor
public class purchaseRESTController {

    @Autowired
    purchaseService purchaseService;

    @GetMapping
    public ResponseEntity<List<purchaseModel>> getAllPurchase() {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.getAllPurchase());
    }

    @GetMapping("/user/{email}")
    public ResponseEntity<List<purchaseModel>> getUserPurchase(@PathVariable String email) {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.getUserPurchases(email));
    }

    @GetMapping("/{mpPaymentId}/items")
    public ResponseEntity<List<Map<String, Object>>> getPurchaseItems(@PathVariable String mpPaymentId) {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.getPurchaseItems(mpPaymentId));
    }

    @PutMapping("/{id}/shipping")
    public ResponseEntity<purchaseModel> updateShippingStatus(@PathVariable Long id, @RequestBody purchaseModel.ShippingStatus status) {
        return ResponseEntity.status(HttpStatus.OK).body(purchaseService.updateShippingStatus(id, status));
    }

}
