package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.dto.PurchasedProductDto;
import com.coronado.blancabakedstore.model.PurchasedProduct;
import com.coronado.blancabakedstore.service.IPurchasedProdService;
import com.coronado.blancabakedstore.service.PurchasedProdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchasedProduct")
@CrossOrigin(origins = "http://localhost:4200")
public class PurchasedProdController {

    private final IPurchasedProdService iPurProdServ;
    @Autowired
    public PurchasedProdController(IPurchasedProdService iPurProdServ){
        this.iPurProdServ = iPurProdServ;
    }

    @PostMapping("/create")
    public ResponseEntity<PurchasedProduct> createPurchasedProduct(@RequestBody PurchasedProductDto purProdDto){
        return new ResponseEntity<>(iPurProdServ.createPurchasedProduct(purProdDto), HttpStatus.OK);
    }
}
