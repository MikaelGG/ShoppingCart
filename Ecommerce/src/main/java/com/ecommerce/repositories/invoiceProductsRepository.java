package com.ecommerce.repositories;

import com.ecommerce.models.invoiceProductsModel;
import com.ecommerce.models.invoicesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface invoiceProductsRepository extends JpaRepository<invoiceProductsModel, Long> {

    List<invoiceProductsModel> findAllByIdInvoice(invoicesModel idInvoice);

}
