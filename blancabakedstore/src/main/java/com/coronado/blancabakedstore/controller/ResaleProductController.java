package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.dto.ExpirationDto;
import com.coronado.blancabakedstore.dto.ResaleProdDto;
import com.coronado.blancabakedstore.dto.ResaleProdWithNearestDateDto;
import com.coronado.blancabakedstore.model.ResaleProduct;
import com.coronado.blancabakedstore.service.IResaleProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resaleProduct")
@CrossOrigin(origins = "http://localhost:4200")
public class ResaleProductController {

    private final IResaleProductService iResaleProdServ;
    @Autowired
    public ResaleProductController(IResaleProductService iResaleProdServ) {
        this.iResaleProdServ = iResaleProdServ;
    }

    @PostMapping("/create")
    public ResponseEntity<ResaleProdWithNearestDateDto> createResaleProduct(@RequestBody  ResaleProdDto resaleProdDto){
        return new ResponseEntity<>(  iResaleProdServ.createResaleProd(resaleProdDto), HttpStatus.CREATED);
    }

    @GetMapping("/getWithNearestDate/{productCode}")
    public ResponseEntity<ResaleProdWithNearestDateDto> getResProdWithNearestDate(@PathVariable int productCode){
        return new ResponseEntity<>(iResaleProdServ.getResaleProdWNearestExpByCode(productCode), HttpStatus.OK);
    }

    @GetMapping("/getWithNearestDateByName/{productName}")
    public ResponseEntity<ResaleProdWithNearestDateDto> getResProdWithNearestDateByDate(@PathVariable String productName){
        return new ResponseEntity<>(iResaleProdServ.getResaleProdWNearestExpByName(productName), HttpStatus.OK);
    }


    @GetMapping("/getAllWithNearestDate")
    public ResponseEntity<List<ResaleProdWithNearestDateDto>> getAllWithNearestDate(){
        return new ResponseEntity<>(iResaleProdServ.getAllWithNearestExpDate(), HttpStatus.OK);
    }

    /*--------------Endpoints without logic in expirationDate--------------------------------------------*/

    @GetMapping("/get/{productCode}")
    public ResponseEntity<ResaleProduct> getResaleProduct(@PathVariable int productCode){
        return new ResponseEntity<>(iResaleProdServ.getResaleProduct(productCode), HttpStatus.OK);
    }

    @GetMapping("/getByProductName/{productName}")
    public ResponseEntity<ResaleProduct> getByProductName(@PathVariable String productName){
        return new ResponseEntity<>(iResaleProdServ.getResaleProductByProdName(productName), HttpStatus.OK);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<ResaleProduct>> getAllResaleProduct(){
        return new ResponseEntity<>(iResaleProdServ.getAllResaleProd(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{productCode}")
    public ResponseEntity<String> deleteResaleProduct(@PathVariable  int productCode){
        return new ResponseEntity<>("Producto borrado correctamente:" + " " + iResaleProdServ.deleteResaleProd(productCode), HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity<String> editResaleProduct(@RequestBody ResaleProdDto resaleProdDto){
        return new ResponseEntity<>("Producto editado con exito: " + iResaleProdServ.editResaleProd(resaleProdDto), HttpStatus.OK);
    }

    @PutMapping("/edit-stock/{productCode}")
    public ResponseEntity<ResaleProduct> updateStock(@PathVariable int productCode, @RequestParam int newStock) {
        return new ResponseEntity<>(iResaleProdServ.updatestock(productCode, newStock), HttpStatus.OK);
    }


}
