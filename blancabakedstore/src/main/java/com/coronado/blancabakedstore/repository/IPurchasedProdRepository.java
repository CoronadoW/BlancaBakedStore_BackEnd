package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.PurchasedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPurchasedProdRepository extends JpaRepository<PurchasedProduct, Long> {
}
