package com.products.repositories;

import com.products.models.productInfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface productInfoRep extends JpaRepository<productInfoModel, String> {
}
