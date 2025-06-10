package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.dto.PurchaseDto;
import com.coronado.blancabakedstore.model.Purchase;
import com.coronado.blancabakedstore.service.IPurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchase")
@CrossOrigin(origins = "http://localhost:4200")
public class PurchaseController {

    private final IPurchaseService iPurServ;

    public PurchaseController(IPurchaseService iPurServ) {
        this.iPurServ = iPurServ;
    }

    @PostMapping("/create")
    public ResponseEntity<Purchase> createPurchase(@RequestBody PurchaseDto purchaseDto){
        return new ResponseEntity<>( iPurServ.createPurchase(purchaseDto), HttpStatus.OK);
    }



}
