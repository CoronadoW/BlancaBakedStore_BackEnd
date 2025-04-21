package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.dto.CashBalanceDto;
import com.coronado.blancabakedstore.model.CashBalance;
import com.coronado.blancabakedstore.service.ICashBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cashBalance")
public class CashBalanceController {

    private final ICashBalanceService iCashBalServ;
    @Autowired
    public CashBalanceController(ICashBalanceService iCashBalServ) {
        this.iCashBalServ = iCashBalServ;
    }

    @PostMapping("/create")
    public ResponseEntity<CashBalance> createCashBalance(@RequestBody CashBalanceDto cashBalanceDto){
        return new ResponseEntity<>( iCashBalServ.createCashBalance(cashBalanceDto), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<CashBalance> getCashBalance(@PathVariable Long id){
        return new ResponseEntity<>(iCashBalServ.getCashBalance(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<CashBalance>> getAllCashBalance(){
        return new ResponseEntity<>(iCashBalServ.getAllCashBalance(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCashBalance(@PathVariable Long id){
        return new ResponseEntity<>("Arqueo de caja borrado con exito: " + iCashBalServ.deleteCashBalance(id), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<String> editCashBalance(@PathVariable Long id,
                                                  @RequestBody CashBalanceDto cashBalanceDto){
        return new ResponseEntity<>("Arqueo de caja editado con exito:" + iCashBalServ.editCashBalance(cashBalanceDto, id), HttpStatus.OK);
    }




}
