package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.PurchaseDto;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.PreSeller;
import com.coronado.blancabakedstore.model.Purchase;
import com.coronado.blancabakedstore.model.PurchasedProduct;
import com.coronado.blancabakedstore.repository.IPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PurchaseService implements IPurchaseService{

    private final IPurchaseRepository iPurRepo;
    private final PurchasedProdService purProdServ;
    private final PreSellerService preSellerServ;
    @Autowired
    public PurchaseService(IPurchaseRepository iPurRepo, PurchasedProdService purProdServ, @Lazy PreSellerService preSellerServ) {
        this.iPurRepo = iPurRepo;
        this.purProdServ = purProdServ;
        this.preSellerServ = preSellerServ;
    }

    public Purchase createPurchase(PurchaseDto purchaseDto){

        Purchase purchase = new Purchase();

         System.out.println("PreSeller dto: " + purchaseDto.getPreSellerName());
         PreSeller preSeller = preSellerServ.getPreSeller(purchaseDto.getPreSellerName());


        List<PurchasedProduct> purchasedProducts = purchaseDto.getPurProdDtoList().stream()
                .map(purProdServ :: createPurchasedProduct)
                .toList();

        Double total = purchasedProducts.stream()
                        .mapToDouble(PurchasedProduct :: getTotalCost)
                        .sum();

        purchase.setPreSeller( preSeller );
        purchase.setPurchaseDate( purchaseDto.getPurchaseDate());
        purchase.setTotalPurchaseCost( total );

        purchasedProducts.forEach(pProd -> pProd.setPurchase(purchase));
        purchase.setPurProdList(purchasedProducts);

        return iPurRepo.save(purchase);
    }

    @Override
    public Purchase getPurchase(Long id) {
        return iPurRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Purchase not found"));
    }

    @Override
    public List<Purchase> getAllPurchase() {
        return iPurRepo.findAll();
    }

    @Override
    public Purchase deletePurchase(Long id) {
        Purchase pur = iPurRepo.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Puchase not found"));
        iPurRepo.deleteById(id);
        return pur;
    }

    @Override
    public List<Purchase> getPurchasesByPreSellerNameOrderedByDate(String preSeller) {
        return iPurRepo.findByPreSeller_PreSellerNameOrderByPurchaseDateDesc(preSeller);
    }


}
