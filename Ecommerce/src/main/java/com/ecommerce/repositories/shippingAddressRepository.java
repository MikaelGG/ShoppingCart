package com.ecommerce.repositories;

import com.ecommerce.models.shippingAddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface shippingAddressRepository extends JpaRepository<shippingAddressModel, Long> {

    List<shippingAddressModel> findAllByIdClient(Long idClient);

    boolean existsByIdClient(Long idClient);

}
