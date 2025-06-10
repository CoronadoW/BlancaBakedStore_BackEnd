package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.PurchaseDto;
import com.coronado.blancabakedstore.model.Purchase;

import java.util.List;

public interface IPurchaseService {

    Purchase createPurchase(PurchaseDto purchaseDto);

    Purchase getPurchase(Long id);

    List<Purchase> getAllPurchase();

    Purchase deletePurchase(Long id);

    List<Purchase> getPurchasesByPreSellerNameOrderedByDate(String preSeller);
}
