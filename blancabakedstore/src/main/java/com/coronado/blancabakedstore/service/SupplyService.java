package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.exceptions.EntityAlreadyExistsException;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.Supply;
import com.coronado.blancabakedstore.repository.ISupplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SupplyService implements ISupplyService{

    private final ISupplyRepository iSuppRepo;
    @Autowired
    public SupplyService(ISupplyRepository iSuppRepo) {
        this.iSuppRepo = iSuppRepo;
    }

    @Override
    public Supply createSupply(Supply supply) {
        if(iSuppRepo.existsBySupplyCode(supply.getSupplyCode())){
            throw new EntityAlreadyExistsException("Insumo ya existe con el codigo: " + supply.getSupplyCode());
        }
        return iSuppRepo.save(supply);
    }

    @Override
    public Supply getSupply(int supplyCode) {
        return iSuppRepo.findBySupplyCode(supplyCode)
                .orElseThrow(() -> new EntityNotFoundException("Insumo no encontrado con el codigo: " + supplyCode));
    }

    @Override
    public List<Supply> getAllSupply() {
        return iSuppRepo.findAll();
    }

    @Override
    @Transactional
    public String deleteSupply(int supplyCode) {
        Supply supp = iSuppRepo.findBySupplyCode(supplyCode)
                .orElseThrow(()-> new EntityNotFoundException("Insumo no encontrado con el codigo: " + supplyCode));
        iSuppRepo.deleteBySupplyCode(supplyCode);
        return "Insumo borrado correctamente: Codigo: " + supp.getSupplyCode() + ", Nombre de Insumo: " + supp.getSupplyName() + ".";
    }

    @Override
    @Transactional
    public Supply editSupply(Supply supply) {
        Supply supp = iSuppRepo.findBySupplyCode(supply.getSupplyCode())
                .orElseThrow(()-> new EntityNotFoundException("Insumo no encontrado con el codigo: " + supply.getSupplyCode()));
        supp.setSupplyCode(supply.getSupplyCode());
        supp.setSupplyName(supply.getSupplyName());
        supp.setQtyPerUnit(supply.getQtyPerUnit());
        supp.setUnit(supply.getUnit());
        supp.setCost(supply.getCost());
        supp.setBuyDate(supply.getBuyDate());
        supp.setExpireDate(supply.getExpireDate());
        supp.setSupplyType(supply.getSupplyType());
        return iSuppRepo.save(supp);
    }

    //Get list of Supply with the same SupplyType
    public List<Supply> getSupplyListBySupplyType(String supplyType){
        List<Supply> supplies = iSuppRepo.findBySupplyTypeContainingIgnoreCase(supplyType);
        if(supplies.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tipo de insumo no encontrado con el nombre: " + supplyType);
        }
        return supplies;
    }

    public List<Supply> getSupplyListBySupplyName(String supplyName){
        List<Supply> supplies = iSuppRepo.findBySupplyNameContainingIgnoreCase(supplyName);
        if(supplies.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Insumo no encontrado con el nombre: " + supplyName);
        }
        return supplies;
    }

    public Supply getSupplyBySupplyName(String supplyName){
        return iSuppRepo.findBySupplyNameIgnoreCase(supplyName)
                .orElseThrow(()-> new EntityNotFoundException("Insumo no encontrado con el nombre: " + supplyName));
    }

}
