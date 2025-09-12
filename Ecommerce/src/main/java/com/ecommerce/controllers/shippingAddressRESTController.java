package com.ecommerce.controllers;

import com.ecommerce.models.shippingAddressModel;
import com.ecommerce.services.shippingAddressService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shipping-addresses")
public class shippingAddressRESTController {

    @Autowired
    shippingAddressService shippingAddressService;

    @PostMapping
    public ResponseEntity<shippingAddressModel> saveShippingAddress(@RequestBody @Valid shippingAddressModel shippingAddressData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(shippingAddressService.saveShippingAddress(shippingAddressData));
    }

    @GetMapping
    public ResponseEntity<List<shippingAddressModel>> getAllShippingAddresses() {
        return ResponseEntity.status(HttpStatus.OK).body(shippingAddressService.getAllShippingAddresses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<shippingAddressModel> getShippingAddress(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(shippingAddressService.getShippingAddress(id));
    }

    @GetMapping("/ShippAdd")
    public ResponseEntity<List<shippingAddressModel>> getShippingAddressByIdClient(@RequestParam Long idClient) {
        return ResponseEntity.status(HttpStatus.OK).body(shippingAddressService.getShippingAddressByIdClient(idClient));
    }

    @PutMapping("/{id}")
    public ResponseEntity<shippingAddressModel> updateShippingAddress(@PathVariable Long id, @RequestBody @Valid shippingAddressModel shippingAddressData) {
        return ResponseEntity.status(HttpStatus.OK).body(shippingAddressService.updateShippingAddress(id, shippingAddressData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShippingAddress(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(shippingAddressService.deleteShippingAddress(id));
    }

}
