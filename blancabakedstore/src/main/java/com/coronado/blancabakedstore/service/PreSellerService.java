package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.repository.IPreSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PreSellerService implements IPreSellerService{

    private final IPreSellerRepository iPreSellerRepo;
    @Autowired

    public PreSellerService(IPreSellerRepository iPreSellerRepo) {
        this.iPreSellerRepo = iPreSellerRepo;
    }



}
