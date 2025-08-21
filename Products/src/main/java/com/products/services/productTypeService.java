package com.products.services;

import com.products.models.productTypeModel;
import com.products.repositories.productTypeRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class productTypeService {

    @Autowired
    productTypeRep productTypeRep;

    @Transactional
    public productTypeModel createProductType(productTypeModel productTypeData) {
        return productTypeRep.save(productTypeData);
    }

    @Transactional(readOnly = true)
    public List<productTypeModel> getAllProductTypes() {
        if (productTypeRep.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No product types available");
        }
        return productTypeRep.findAll();
    }

    @Transactional
    public productTypeModel updateProductType(Long id, productTypeModel productTypeData) {
        productTypeModel productType = productTypeRep.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product type not found"));
        productType.setNameType(productTypeData.getNameType());
        return productTypeRep.save(productType);
    }

    @Transactional
    public String deleteProductType(Long id) {
        if (!productTypeRep.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product type not found");
        }
        productTypeRep.deleteById(id);
        return "Product type deleted successfully";
    }

}
