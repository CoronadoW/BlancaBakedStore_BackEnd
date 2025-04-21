package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {

    boolean existsByUserName(String userName);

    Optional<User> findByUserName(String userName);

    void deleteByUserName(String userName);
}
