package com.ecommerce.services;

import com.ecommerce.dtos.ClientDTO;
import com.ecommerce.dtos.ProductsDTO;
import com.ecommerce.dtos.invoiceDTO;
import com.ecommerce.dtos.ProductDTO;
import com.ecommerce.models.invoiceProductsModel;
import com.ecommerce.models.invoicesModel;
import com.ecommerce.models.shippingAddressModel;
import com.ecommerce.repositories.invoiceProductsRepository;
import com.ecommerce.repositories.invoicesRepository;
import com.ecommerce.repositories.shippingAddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class invoicesService {

    @Autowired
    invoicesRepository invoicesRepository;

    @Autowired
    invoiceProductsRepository invoicesProductsRepository;

    @Autowired
    shippingAddressRepository shippingAddressRepository;

    @Autowired
    RestTemplate restTemplate;

    @Transactional
    public invoiceDTO saveInvoice(invoiceDTO invoiceData) {
        invoicesModel invoice = new invoicesModel();
        System.out.println("Invoice Data: " + invoiceData);
        invoice.setInvoiceDate(invoiceData.getInvoiceDate());
        invoice.setIdClient(invoiceData.getIdClient().getId());
        shippingAddressModel shippingAddress = shippingAddressRepository.findById(invoiceData.getShippingAddress().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping address not found"));
        invoice.setShippingAddress(shippingAddress);
        invoice.setSubtotal(invoiceData.getSubtotal());
        invoice.setDiscount(invoiceData.getDiscount());
        invoice.setTax(invoiceData.getTax());
        invoice.setTotal(invoiceData.getTotal());
        invoicesRepository.save(invoice);
        ClientDTO client = restTemplate.getForObject("http://localhost:8081/api/users/" + invoiceData.getIdClient().getId(), ClientDTO.class);
        invoiceData.getIdClient().setFullName(client.getFullName());
        invoiceData.getIdClient().setEmail(client.getEmail());
        invoiceData.getIdClient().setPhoneNumber(client.getPhoneNumber());
        invoiceData.getProducts().stream().forEach(inv -> {
            System.out.println("Processing Product: " + inv.getIdProduct());
            ProductDTO product = restTemplate.getForObject("http://localhost:8082/api/products/" + inv.getIdProduct().getCode(), ProductDTO.class);
            System.out.println("Product Data: " + product);
            System.out.println("Product type: " + product.getProductType());
            inv.getIdProduct().setCode(product.getCode());
            inv.getIdProduct().setPhoto(product.getPhoto());
            inv.getIdProduct().setName(product.getName());
            inv.getIdProduct().setDescription(product.getDescription());
            inv.getIdProduct().setPrice(product.getPrice());
            inv.getIdProduct().setProductType(product.getProductType());
            invoiceProductsModel invoiceProduct = new invoiceProductsModel();
            invoiceProduct.setIdInvoice(invoice);
            invoiceProduct.setIdProduct(inv.getIdProduct().getCode());
            invoiceProduct.setQuantity(inv.getQuantity());
            invoiceProduct.setUnitPrice(inv.getUnitPrice());
            invoiceProduct.setTotalProduct(inv.getTotalProduct());
            invoicesProductsRepository.save(invoiceProduct);
        });
        return invoiceData;
    }

    @Transactional(readOnly = true)
    public List<invoiceDTO> getAllInvoices() {
        if (invoicesRepository.findAll().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No invoices available");
        }
        List<invoicesModel> invoices = invoicesRepository.findAll();
        return invoices.stream().map(inv -> {
            invoiceDTO invoiceDTO = new invoiceDTO();
            invoiceDTO.setInvoiceDate(inv.getInvoiceDate());
            ClientDTO client = restTemplate.getForObject("http://localhost:8081/api/users/" + inv.getIdClient(), ClientDTO.class);
            invoiceDTO.setIdClient(client);
            shippingAddressModel shippingAddress = shippingAddressRepository.findById(inv.getShippingAddress().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Shipping address not found"));
            invoiceDTO.setShippingAddress(shippingAddress);
            List<invoiceProductsModel> invoiceProducts = invoicesProductsRepository.findAllByIdInvoice(inv);
            invoiceProducts.stream().forEach(infProd -> {
                ProductsDTO productsDTO = new ProductsDTO();
                ProductDTO product = restTemplate.getForObject("http://localhost:8082/api/products/" + infProd.getIdProduct(), ProductDTO.class);
                productsDTO.setIdProduct(product);
                productsDTO.setQuantity(infProd.getQuantity());
                productsDTO.setUnitPrice(infProd.getUnitPrice());
                productsDTO.setTotalProduct(infProd.getTotalProduct());
                invoiceDTO.getProducts().add(productsDTO);
            });
            invoiceDTO.setSubtotal(inv.getSubtotal());
            invoiceDTO.setDiscount(inv.getDiscount());
            invoiceDTO.setTax(inv.getTax());
            invoiceDTO.setTotal(inv.getTotal());
            return invoiceDTO;
        }).toList();
    }

    @Transactional
    public String deleteInvoice(String invoiceNumber) {
        if (!invoicesRepository.existsById(invoiceNumber)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invoice not found");
        }
        invoicesRepository.deleteById(invoiceNumber);
        return "Invoice with number " + invoiceNumber + " has been deleted successfully";
    }


}
