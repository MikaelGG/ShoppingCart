package com.products.repositories;

import com.products.models.productInfoModel;
import com.products.models.productTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productInfoRep extends JpaRepository<productInfoModel, String> {

    List<productInfoModel> findByProductType(productTypeModel productType);

}
