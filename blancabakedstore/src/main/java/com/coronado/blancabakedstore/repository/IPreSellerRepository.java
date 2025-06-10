package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.PreSeller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IPreSellerRepository extends JpaRepository<PreSeller, Long> {


    Optional<PreSeller> findByPreSellerName(String preSellerName);

    boolean existsByPreSellerName(String preSellerName);

    void deleteByPreSellerName(String preSellerName);

    List<PreSeller> findByPreSellerNameContainingIgnoreCase(String preSellerName);

    Page<PreSeller> findAll(Pageable pageable);

}
