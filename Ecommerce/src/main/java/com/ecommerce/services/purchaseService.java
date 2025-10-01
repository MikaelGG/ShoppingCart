package com.ecommerce.services;

import com.ecommerce.models.purchaseModel;
import com.ecommerce.repositories.purchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@Service
public class purchaseService {

    @Autowired
    purchaseRepository purchaseRepository;

    @Transactional(readOnly = true)
    public List<purchaseModel> getAllPurchase() {
        return purchaseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<purchaseModel> getUserPurchases(@PathVariable String email) {
        return purchaseRepository.findByBuyerEmail(email);
    }

    @Transactional
    public purchaseModel updateShippingStatus(
            @PathVariable Long id,
            @RequestBody Map<String, String> body) {
        purchaseModel purchase = purchaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada"));

        String status = body.get("status");
        purchase.setShippingStatus(purchaseModel.ShippingStatus.valueOf(status));

        return purchaseRepository.save(purchase);
    }
}
