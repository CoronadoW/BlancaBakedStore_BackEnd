package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.dto.PreSellerDto;
import com.coronado.blancabakedstore.model.PreSeller;
import com.coronado.blancabakedstore.model.Purchase;
import com.coronado.blancabakedstore.service.IPreSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/preSeller")
@CrossOrigin(origins = "http://localhost:4200")
public class PreSellerController {

    private final IPreSellerService iPreSellerServ;
    @Autowired
    public PreSellerController(IPreSellerService iPreSellerServ) {
        this.iPreSellerServ = iPreSellerServ;
    }

    @PostMapping("/create")
    public ResponseEntity<PreSeller> createPreSeller(@RequestBody PreSellerDto preSellerDto){
        return new ResponseEntity<>(iPreSellerServ.createPreSeller(preSellerDto), HttpStatus.CREATED);
    }

    @GetMapping("/get/{preSellerName}")
    public ResponseEntity<PreSeller> getPreSeller(@PathVariable String preSellerName){
        return new ResponseEntity<>(iPreSellerServ.getPreSeller(preSellerName), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<PreSeller>> getAllPreSellers(){
        return new ResponseEntity<>(iPreSellerServ.getAllPreSellers(), HttpStatus.OK);
    }

    @GetMapping("/getAllPaginated")
    public ResponseEntity<Page<PreSeller>> getAllPreSellersPaginated(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "preSellerName") String sortBy
    ){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return new ResponseEntity<>(iPreSellerServ.getAllPreSellersPaginated(pageable), HttpStatus.OK);
    }

    @GetMapping("/getByPartialName/{preSellerName}")
    public ResponseEntity<List<PreSeller>> getPreSellerContainingName(@PathVariable String preSellerName){
        return new ResponseEntity<>(iPreSellerServ.getPreSellerContainingName(preSellerName), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{preSellerName}")
    public ResponseEntity<String> deletePreSeller(@PathVariable String preSellerName){
        iPreSellerServ.deletePreSeller(preSellerName);
        return new ResponseEntity<>( "PreSeller deleted succesfully", HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<PreSeller> editPreSeller(@RequestBody PreSellerDto preSellerDto){
        return new ResponseEntity<>(iPreSellerServ.editPreSeller(preSellerDto), HttpStatus.OK);
    }

    @GetMapping("/getPurchaseList/{preSellerName}")
    public ResponseEntity<List<Purchase>> getPurchaseListByPreSeller(@PathVariable String preSellerName){
        return new ResponseEntity<>(iPreSellerServ.getPurchaseListByPreSeller(preSellerName), HttpStatus.OK);
    }

    @GetMapping("/getPurchases/{preSellerName}")
    public ResponseEntity<List<Purchase>> getPurchasesByPreSellerOrderedByDate(@PathVariable String preSellerName){
        return new ResponseEntity<>(iPreSellerServ.getPurchasesByPreSellerOrderedByDate(preSellerName), HttpStatus.OK);
    }

}
