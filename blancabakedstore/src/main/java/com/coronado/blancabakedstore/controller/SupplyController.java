package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.model.Supply;
import com.coronado.blancabakedstore.service.ISupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/supply")
public class SupplyController {

    private final ISupplyService iSuppServ;
    @Autowired
    public SupplyController(ISupplyService iSuppServ) {
        this.iSuppServ = iSuppServ;
    }

    @PostMapping("/create")
    public ResponseEntity<Supply> createSupply(@RequestBody  Supply supply){
        return new ResponseEntity<>(iSuppServ.createSupply(supply), HttpStatus.CREATED);
    }

    @GetMapping("/get/{supplyCode}")
    public ResponseEntity<Supply> getSuppy(@PathVariable  int supplyCode){
        return new ResponseEntity<>(iSuppServ.getSupply(supplyCode), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Supply>> getAllSupply(){
        return new ResponseEntity<>(iSuppServ.getAllSupply(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{supplyCode}")
    public ResponseEntity<String> deleteSupply(@PathVariable int supplyCode){
        return new ResponseEntity<>(iSuppServ.deleteSupply(supplyCode), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<Supply> editSupply(@RequestBody Supply supply){
        return new ResponseEntity<>(iSuppServ.editSupply(supply), HttpStatus.OK);
    }

    @GetMapping("/getBySupplyType/{supplyType}")
    public ResponseEntity<List<Supply>> getSupplyBySuppyType(@PathVariable String supplyType){
        return new ResponseEntity<>(iSuppServ.getSupplyListBySupplyType(supplyType), HttpStatus.OK);
    }

    @GetMapping("/getSupplyListBySupplyName/{supplyName}")
    public ResponseEntity<List<Supply>> getSupplyListBySupplyName(@PathVariable String supplyName){
        return new ResponseEntity<>(iSuppServ.getSupplyListBySupplyName(supplyName), HttpStatus.OK);
    }

    @GetMapping("/getSupplyBySupplyName/{supplyName}")
    public ResponseEntity<Supply> getSupplyBySupplyName(@PathVariable String supplyName){
        return new ResponseEntity<>(iSuppServ.getSupplyBySupplyName(supplyName), HttpStatus.OK);
    }
}
