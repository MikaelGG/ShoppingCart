package com.users.repositories;

import com.users.models.userTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userTypeRepository extends JpaRepository<userTypeModel, Long> {

}
