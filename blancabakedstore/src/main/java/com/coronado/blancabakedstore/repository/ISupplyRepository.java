package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ISupplyRepository extends JpaRepository <Supply,Long>{

    Optional<Supply> findBySupplyCode(int supplyCode);

    boolean existsBySupplyCode(int supplyCode);

    void deleteBySupplyCode(int supplyCode);

    List<Supply> findBySupplyNameContainingIgnoreCase(String supplyName);

    Optional<Supply> findBySupplyNameIgnoreCase(String supplyName);

    List<Supply> findBySupplyTypeContainingIgnoreCase(String supplyType);

    boolean existsBySupplyName(String supplyName);

}
