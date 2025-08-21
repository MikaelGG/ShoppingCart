package com.products.repositories;

import com.products.models.productTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productTypeRep extends JpaRepository<productTypeModel, Long> {
}
