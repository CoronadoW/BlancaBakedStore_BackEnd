package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.service.IPreSellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/preSeller")
public class PreSellerController {

    private final IPreSellerService iPreSellerServ;
    @Autowired

    public PreSellerController(IPreSellerService iPreSellerServ) {
        this.iPreSellerServ = iPreSellerServ;
    }



}
