package com.ecommerce.repositories;

import com.ecommerce.models.invoicesModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface invoicesRepository extends JpaRepository<invoicesModel, String> {
}
