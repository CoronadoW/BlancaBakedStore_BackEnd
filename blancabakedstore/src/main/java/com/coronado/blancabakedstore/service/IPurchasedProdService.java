package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.PurchasedProductDto;
import com.coronado.blancabakedstore.model.PurchasedProduct;

import java.util.List;

public interface IPurchasedProdService {

PurchasedProduct createPurchasedProduct(PurchasedProductDto purProdDto);

PurchasedProduct getPurchasedProduct(Long id);

List<PurchasedProduct> getAllPurchasedProduct();

PurchasedProduct deletePurchasedProduct(Long id);

}
