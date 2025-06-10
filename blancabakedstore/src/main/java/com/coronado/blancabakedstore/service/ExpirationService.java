package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.ExpirationDto;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.Expiration;
import com.coronado.blancabakedstore.model.ResaleProduct;
import com.coronado.blancabakedstore.repository.IExpirationRepository;
import com.coronado.blancabakedstore.repository.IResaleProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ExpirationService implements IExpirationService{

    private final IExpirationRepository iExpRepo;
    private final IResaleProductRepository iResProdRepo;
    @Autowired
    public ExpirationService(IExpirationRepository iExpRepo, IResaleProductRepository iResProdRepo) {
        this.iExpRepo = iExpRepo;
        this.iResProdRepo = iResProdRepo;
    }



    @Override
    public Expiration createExpirationAssociatedToProduct(ExpirationDto expDto) {
        System.out.println("Product code recibido: " + expDto.getProductCode());

        ResaleProduct resaleProduct = iResProdRepo.findByProductCode(expDto.getProductCode())
                .orElseThrow(()-> new EntityNotFoundException("Resale product not found"));

        Expiration exp = new Expiration();
        exp.setQty(expDto.getQty());
        exp.setBuyDate(expDto.getBuyDate());
        exp.setExpireDate(expDto.getExpireDate());
        exp.setResaleProduct(resaleProduct);

        iExpRepo.save(exp);

        resaleProduct.getExpirations().add(exp);

        updateStockToProduct(resaleProduct);

        return exp;
    }

    public void updateStockToProduct(ResaleProduct resaleProduct){

        int totalStock = resaleProduct.getExpirations().stream()
                .mapToInt(Expiration ::getQty)
                .sum();
        resaleProduct.setStock(totalStock);
        iResProdRepo.save(resaleProduct);
    }

    @Transactional
   public void updateExpirations(ExpirationDto expirationDto){

        List<Expiration> expirationList = iExpRepo.findAllByResaleProductProductCodeOrderByExpireDateAsc(expirationDto.getProductCode());

        if(expirationList.isEmpty()){
            throw new EntityNotFoundException("Expirations not found for ResaleProduct with product code: " + expirationDto.getProductCode());
        }

        int qtyToConsume = expirationDto.getQty();

    for(Expiration exp : expirationList){
        if(qtyToConsume <= 0) break;

        int available = exp.getQty();

        if(available  >= qtyToConsume){
            exp.setQty(available - qtyToConsume);
            qtyToConsume = 0;
        }else {
            exp.setQty(0);
            qtyToConsume -= available;
        }
        iExpRepo.save(exp);
    }
       if (qtyToConsume > 0) {
           throw new IllegalArgumentException("Insufficient stock to fulfill request. Missing: " + qtyToConsume);
       }

       ResaleProduct resaleProduct = iResProdRepo.findByProductCode(expirationDto.getProductCode())
               .orElseThrow(()-> new EntityNotFoundException("Resale product not found"));
       updateStockToProduct(resaleProduct);
   }

   //Not in use
    public Expiration getOldestExpirationByProductCode(int productCode){
        return iExpRepo.findFirstByResaleProductProductCodeOrderByExpireDateAsc(productCode)
                .orElseThrow(()->new EntityNotFoundException("Expiration not found"));
    }

    @Override
    public Expiration getNearestExpirationByProductCode(int productCode) {
        return null;
    }

    @Override
    //Get Expirations List associated to a Product that contain productCode
    public List<Expiration> getExpirationsByProductCode(int productCode) {
        return iExpRepo.findByResaleProductProductCode(productCode);
    }

    @Override
    //Get the Expiration that has the nearest expirationDate to LocalDate.now( TODAY ).
    public Expiration getNearestExpirationDate() {
        return iExpRepo.findFirstByExpireDateGreaterThanEqualOrderByExpireDateAsc(LocalDate.now())
                .orElseThrow(()-> new EntityNotFoundException("Date not found"));
    }

    @Override
    //Get all Expirations in order by date, showing the nearest to LocalDate.now ( TODAY ) first.
    public List<Expiration> getExpirationsListByOrder() {
        return iExpRepo.findAllByExpireDateGreaterThanEqualOrderByExpireDateAsc(LocalDate.now());
    }


    //Method to create expiration without association to ResaleProduct---Not in Use.
    @Override
    public Expiration createExpiration(ExpirationDto expDto) {
        Expiration expiration = new Expiration();
        expiration.setQty(expDto.getQty());
        expiration.setBuyDate(expDto.getBuyDate());
        expiration.setExpireDate(expDto.getExpireDate());

        return iExpRepo.save(expiration);
    }
}
