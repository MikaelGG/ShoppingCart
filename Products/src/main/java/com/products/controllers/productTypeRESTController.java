package com.products.controllers;

import com.products.models.productTypeModel;
import com.products.services.productTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-types")
public class productTypeRESTController {

    @Autowired
    productTypeService productTypeService;

    @PostMapping
    public ResponseEntity<productTypeModel> createProductType(@RequestBody @Valid productTypeModel productTypeData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productTypeService.createProductType(productTypeData));
    }

    @GetMapping
    public ResponseEntity<List<productTypeModel>> getAllProductTypes() {
        return ResponseEntity.status(HttpStatus.OK).body(productTypeService.getAllProductTypes());
    }

    @PutMapping("/{id}")
    public ResponseEntity<productTypeModel> updateProductType(@PathVariable Long id, @RequestBody @Valid productTypeModel productTypeData) {
        return ResponseEntity.status(HttpStatus.OK).body(productTypeService.updateProductType(id, productTypeData));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductType(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(productTypeService.deleteProductType(id));
    }

}
