package com.ecommerce.repositories;

import com.ecommerce.models.shippingAddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface shippingAddressRepository extends JpaRepository<shippingAddressModel, Long> {
}
