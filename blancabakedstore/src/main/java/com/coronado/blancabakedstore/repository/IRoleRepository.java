package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleType(String roleType);

    Optional<Role> findByRoleType(String roleType);

    void deleteByRoleType(String roleType);
}
