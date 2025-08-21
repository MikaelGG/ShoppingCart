package com.users.repositories;

import com.users.models.usersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface usersRepository extends JpaRepository<usersModel, Long> {
}
