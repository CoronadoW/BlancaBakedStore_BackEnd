package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.dto.ExpirationDto;
import com.coronado.blancabakedstore.model.Expiration;
import com.coronado.blancabakedstore.service.IExpirationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expiration")
@CrossOrigin(origins = "http://localhost:4200")
public class ExpirationController {

    private final IExpirationService iExpServ;
    @Autowired
    public ExpirationController(IExpirationService iExpServ) {
        this.iExpServ = iExpServ;
    }

    @PostMapping("/createAndAssocProd")
    public ResponseEntity<Expiration> createAndAssociateProduct(@RequestBody ExpirationDto expDto){
        return new ResponseEntity<>(iExpServ.createExpirationAssociatedToProduct(expDto), HttpStatus.OK);
    }

    @PostMapping("/updateStock")
    public ResponseEntity<Void> updateStock(@RequestBody ExpirationDto expirationDto){
        iExpServ.updateExpirations(expirationDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }



/*----------------------------------------------------------------------------*/


    @GetMapping("/byProductCode/{productCode}")
    public ResponseEntity<List<Expiration>> getExpirationsByProductCode(@PathVariable int productCode){
        return new ResponseEntity<>(iExpServ.getExpirationsByProductCode(productCode), HttpStatus.OK);
    }

    @GetMapping("/getNearest")
    public ResponseEntity<Expiration> getNearestExpiration(){
        return new ResponseEntity<>(iExpServ.getNearestExpirationDate(), HttpStatus.OK);
    }

    @GetMapping("/order")
    public ResponseEntity<List<Expiration>> orderExpirationsByExpirationDate(){
        return new ResponseEntity<>(iExpServ.getExpirationsListByOrder(), HttpStatus.OK);
    }
}
