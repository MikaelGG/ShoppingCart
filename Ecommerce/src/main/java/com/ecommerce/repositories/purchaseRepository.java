package com.ecommerce.repositories;

import com.ecommerce.models.purchaseModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface purchaseRepository extends JpaRepository<purchaseModel, Long> {

    Optional<purchaseModel> findByMpPaymentId(String mpPaymentId);

    List<purchaseModel> findByBuyerEmail(String email);

}
