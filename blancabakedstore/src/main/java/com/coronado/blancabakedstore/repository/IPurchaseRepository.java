package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.PreSeller;
import com.coronado.blancabakedstore.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPurchaseRepository extends JpaRepository<Purchase, Long> {

    List<Purchase> findByPreSeller_PreSellerNameOrderByPurchaseDateDesc(String preSellerName);

}
