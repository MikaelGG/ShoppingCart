package com.users.repositories;

import com.users.models.UserTypeModel;
import com.users.models.UsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersModel, Long> {

    Optional<UsersModel> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByUserType(UserTypeModel userType);

    List<UsersModel> findByUserType(UserTypeModel userType);

}
