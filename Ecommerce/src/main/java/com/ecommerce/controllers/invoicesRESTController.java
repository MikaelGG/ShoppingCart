package com.ecommerce.controllers;

import com.ecommerce.dtos.invoiceDTO;
import com.ecommerce.services.invoicesService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
public class invoicesRESTController {

    @Autowired
    invoicesService invoicesService;

    @PostMapping
    public ResponseEntity<invoiceDTO> saveInvoice(@RequestBody @Valid invoiceDTO invoiceData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(invoicesService.saveInvoice(invoiceData));
    }

    @GetMapping
    public ResponseEntity<List<invoiceDTO>> getAllInvoices() {
        return ResponseEntity.status(HttpStatus.OK).body(invoicesService.getAllInvoices());
    }

    @DeleteMapping("/{invoiceNumber}")
    public ResponseEntity<String> deleteInvoice(@PathVariable String invoiceNumber) {
        return ResponseEntity.status(HttpStatus.OK).body(invoicesService.deleteInvoice(invoiceNumber));
    }

}
