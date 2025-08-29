package com.products.repositories;

import com.products.models.productInfoModel;
import com.products.models.productTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface productInfoRep extends JpaRepository<productInfoModel, String> {

    List<productInfoModel> findByProductType(productTypeModel productType);

    @Query(value = """
    SELECT DISTINCT * FROM product_info 
    WHERE (name ILIKE %?1% OR description ILIKE %?1%)
    OR EXISTS (
        SELECT 1 FROM unnest(string_to_array(?1, ' ')) AS word
        WHERE name ILIKE '%' || word || '%' OR description ILIKE '%' || word || '%'
    )
    """, nativeQuery = true)
    List<productInfoModel> findByName(String name);

}
