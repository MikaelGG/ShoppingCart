package com.products.controllers;

import com.products.models.productInfoModel;
import com.products.models.productTypeModel;
import com.products.services.productInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class productInfoRESTController {

    @Autowired
    productInfoService productInfoService;

    @PostMapping
    public ResponseEntity<productInfoModel> createProductInfo(@RequestBody @Valid productInfoModel productInfoData) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productInfoService.saveProductInfo(productInfoData));
    }

    @GetMapping
    public ResponseEntity<List<productInfoModel>> getAllProductInfo() {
        return ResponseEntity.status(HttpStatus.OK).body(productInfoService.getAllProductInfo());
    }

    @GetMapping("/product/{productType}")
    public ResponseEntity<List<productInfoModel>> getProductsByType(@PathVariable productTypeModel productType) {
        return ResponseEntity.status(HttpStatus.OK).body(productInfoService.getProductsByType(productType));
    }

    @GetMapping("/{code}")
    public ResponseEntity<productInfoModel> getProductInfo(@PathVariable String code) {
        return ResponseEntity.status(HttpStatus.OK).body(productInfoService.getProductInfo(code));
    }

    @GetMapping("/searcher")
    public ResponseEntity<List<productInfoModel>> getByNameSearch(@RequestParam String name) {
        return ResponseEntity.status(HttpStatus.OK).body(productInfoService.getByNameSearch(name));
    }

    @PutMapping("/{code}")
    public ResponseEntity<productInfoModel> updateProductInfo(@PathVariable String code, @RequestBody @Valid productInfoModel productInfoData){
        return ResponseEntity.status(HttpStatus.OK).body(productInfoService.updateProductInfo(code, productInfoData));
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<String> deleteProductInfo(@PathVariable String code) {
        return ResponseEntity.status(HttpStatus.OK).body(productInfoService.deleteProductInfo(code));
    }
}
