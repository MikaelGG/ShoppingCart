package com.ecommerce.services;

import com.ecommerce.dtos.shippingAddressDTO;
import com.ecommerce.models.shippingAddressModel;
import com.ecommerce.repositories.shippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class shippingAddressService {

    @Autowired
    shippingAddressRepository shippingAddressRepository;

    @Transactional
    public shippingAddressModel saveShippingAddress(shippingAddressModel shippingAddressData) {
        return shippingAddressRepository.save(shippingAddressData);
    }

    @Transactional(readOnly = true)
    public List<shippingAddressModel> getAllShippingAddresses() {
        return shippingAddressRepository.findAll();
    }

    @Transactional(readOnly = true)
    public shippingAddressModel getShippingAddress(Long id) {
        return shippingAddressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping address not found"));
    }

    @Transactional(readOnly = true)
    public List<shippingAddressModel> getShippingAddressByIdClient(Long idClient) {
        return shippingAddressRepository.findAllByIdClient(idClient);
    }

    @Transactional
    public shippingAddressModel updateShippingAddress(Long id , shippingAddressModel shippingAddressData) {
        shippingAddressModel existingAddress = shippingAddressRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping address not found"));
        existingAddress.setAddressLine1(shippingAddressData.getAddressLine1());
        existingAddress.setAddressLine2(shippingAddressData.getAddressLine2());
        existingAddress.setCity(shippingAddressData.getCity());
        existingAddress.setRegion(shippingAddressData.getRegion());
        existingAddress.setPostalCode(shippingAddressData.getPostalCode());
        existingAddress.setCountry(shippingAddressData.getCountry());
        existingAddress.setFullName(shippingAddressData.getFullName());
        existingAddress.setPhone(shippingAddressData.getPhone());
        existingAddress.setIdClient(shippingAddressData.getIdClient());
        return shippingAddressRepository.save(existingAddress);
    }

    @Transactional
    public String deleteShippingAddress(Long id) {
        if (!shippingAddressRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping address not found");
        }
        shippingAddressRepository.deleteById(id);
        return "Shipping address deleted successfully";
    }


}
