package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.PurchasedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPurchasedProdRepository extends JpaRepository<PurchasedProduct, Long> {


}
