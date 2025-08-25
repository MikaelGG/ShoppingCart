package com.products.services;

import com.products.models.productInfoModel;
import com.products.models.productTypeModel;
import com.products.repositories.productInfoRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class productInfoService {

    @Autowired
    productInfoRep productInfoRep;

    @Transactional
    public productInfoModel saveProductInfo(productInfoModel productInfoData) {
        return productInfoRep.save(productInfoData);
    }

    @Transactional(readOnly = true)
    public List<productInfoModel> getAllProductInfo() {
        return productInfoRep.findAll();
    }

    @Transactional(readOnly = true)
    public List<productInfoModel> getProductsByType(productTypeModel productType) {
        return productInfoRep.findByProductType(productType);
    }

    @Transactional(readOnly = true)
    public productInfoModel getProductInfo(String code) {
        return productInfoRep.findById(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found with code: " + code));
    }

    @Transactional
    public productInfoModel updateProductInfo(String code, productInfoModel productInfoData) {
        productInfoModel productInfo = productInfoRep.findById(code).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found with code: " + code));
        productInfo.setPhoto(productInfoData.getPhoto());
        productInfo.setName(productInfoData.getName());
        productInfo.setDescription(productInfoData.getDescription());
        productInfo.setQuantity(productInfoData.getQuantity());
        productInfo.setPrice(productInfoData.getPrice());
        return productInfoRep.save(productInfo);
    }

    @Transactional
    public String deleteProductInfo(String code) {
        if (!productInfoRep.existsById(code)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No product found with code: " + code);
        }
        productInfoRep.deleteById(code);
        return "Product deleted successfully with code: " + code;
    }

}
