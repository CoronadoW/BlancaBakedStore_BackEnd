package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.PurchasedProductDto;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.PurchasedProduct;
import com.coronado.blancabakedstore.repository.IPurchasedProdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchasedProdService implements IPurchasedProdService{

private final IPurchasedProdRepository iPurProdRepo;
    @Autowired
    public PurchasedProdService(IPurchasedProdRepository iPurProdRepo){
        this.iPurProdRepo = iPurProdRepo;
    }


    @Override
    public PurchasedProduct createPurchasedProduct(PurchasedProductDto purProdDto) {
        PurchasedProduct purchasedProduct = new PurchasedProduct();
        purchasedProduct.setPurProdName(purProdDto.getName());
        purchasedProduct.setPurProdBrand(purProdDto.getBrand());
        purchasedProduct.setPurProdUnitCost(purProdDto.getUnitCost());
        purchasedProduct.setPurProdQty(purProdDto.getQty());
        purchasedProduct.setTotalCost(  calculateTotalCost(purProdDto) );

        return iPurProdRepo.save(purchasedProduct);
    }

    @Override
    public PurchasedProduct getPurchasedProduct(Long id) {
        return null;
    }

    @Override
    public List<PurchasedProduct> getAllPurchasedProduct() {
        return iPurProdRepo.findAll();
    }

    @Override
    public PurchasedProduct deletePurchasedProduct(Long id) {
        PurchasedProduct purProd = iPurProdRepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Purchased product not found"));
        iPurProdRepo.deleteById(id);
        return purProd;
    }

    public Double calculateTotalCost(PurchasedProductDto purchasedProductDto){
        Double unitCost = purchasedProductDto.getUnitCost();
        Double qty = purchasedProductDto.getQty();
        return unitCost * qty;
    }
}
