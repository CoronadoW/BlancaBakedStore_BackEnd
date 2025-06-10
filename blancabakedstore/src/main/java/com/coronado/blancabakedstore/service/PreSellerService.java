package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.PreSellerDto;
import com.coronado.blancabakedstore.dto.PurchaseDto;
import com.coronado.blancabakedstore.exceptions.EntityAlreadyExistsException;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.PreSeller;
import com.coronado.blancabakedstore.model.Purchase;
import com.coronado.blancabakedstore.repository.IPreSellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreSellerService implements IPreSellerService{

    private final IPreSellerRepository iPreSellerRepo;
    private final PurchaseService purServ;
    @Autowired
    public PreSellerService(IPreSellerRepository iPreSellerRepo, PurchaseService purServ) {

        this.iPreSellerRepo = iPreSellerRepo;
        this.purServ = purServ;
    }


    @Override
    public PreSeller createPreSeller(PreSellerDto preSellerDto) {
        if(iPreSellerRepo.existsByPreSellerName(preSellerDto.getPreSellerName())){
           // throw new EntityAlreadyExistsException("PreSeller already exists with pre seller name: "+ preSellerDto.getPreSellerName());
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Proveedor ya existente con nombre: " + preSellerDto.getPreSellerName());
        }
        PreSeller preSeller = new PreSeller();
        preSeller.setPreSellerName(preSellerDto.getPreSellerName());
        preSeller.setPreSellerBrand(preSellerDto.getPreSellerBrand());
        preSeller.setPreSellerPhone(preSellerDto.getPreSellerPhone());
        preSeller.setAddress(preSellerDto.getAddress());
        preSeller.setEmail(preSellerDto.getEmail());
        return iPreSellerRepo.save(preSeller);
    }

    @Override
    public PreSeller getPreSeller(String preSellerName) {
        return iPreSellerRepo.findByPreSellerName(preSellerName)
                .orElseThrow(()-> new EntityNotFoundException("PreSeller actually not found"));
    }

    @Override
    public List<PreSeller> getAllPreSellers() {
        return iPreSellerRepo.findAll();
    }

    @Override
    public Page<PreSeller> getAllPreSellersPaginated(Pageable pageable) {
        return iPreSellerRepo.findAll(pageable);
    }

    @Override
    public List<PreSeller> getPreSellerContainingName(String preSellerName){
        return iPreSellerRepo.findByPreSellerNameContainingIgnoreCase(preSellerName);
    }

    @Override
    public void deletePreSeller(String preSellerName) {
        if(iPreSellerRepo.existsByPreSellerName(preSellerName)){
            iPreSellerRepo.deleteByPreSellerName(preSellerName);
            return;
        }
        throw new EntityNotFoundException("PreSeller not found");
    }

    public PreSeller editPreSeller(PreSellerDto preSellerDto){
        PreSeller preSeller =  iPreSellerRepo.findByPreSellerName(preSellerDto.getPreSellerName())
                .orElseThrow(()-> new EntityNotFoundException("PreSeller not found"));
        preSeller.setPreSellerName(preSellerDto.getPreSellerName());
        preSeller.setPreSellerBrand(preSellerDto.getPreSellerBrand());
        preSeller.setPreSellerPhone(preSellerDto.getPreSellerPhone());
        preSeller.setAddress(preSellerDto.getAddress());
        preSeller.setEmail(preSellerDto.getEmail());
        return iPreSellerRepo.save(preSeller);
    }

    //Not used finally
    @Override
    public List<Purchase> getPurchaseListByPreSeller(String preSellerName) {
        PreSeller preSeller = iPreSellerRepo.findByPreSellerName(preSellerName)
                .orElseThrow(() -> new EntityNotFoundException("PreSeller not found"));
        return preSeller.getPurchaseList().stream()
                .sorted(Comparator.comparing(Purchase :: getPurchaseDate).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public List<Purchase> getPurchasesByPreSellerOrderedByDate(String preSellerName) {
        return purServ.getPurchasesByPreSellerNameOrderedByDate(preSellerName);
    }




}
