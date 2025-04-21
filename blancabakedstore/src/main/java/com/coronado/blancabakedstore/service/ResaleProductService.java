package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.ResaleProdDto;
import com.coronado.blancabakedstore.exceptions.EntityAlreadyExistsException;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.ResaleProduct;
import com.coronado.blancabakedstore.repository.IResaleProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ResaleProductService implements IResaleProductService{

    private final IResaleProductRepository iResaleProdRepo;
    @Autowired
    public ResaleProductService(IResaleProductRepository iResaleProdRepo) {
        this.iResaleProdRepo = iResaleProdRepo;
    }


    @Override
    @Transactional
    public ResaleProduct createResaleProd(ResaleProdDto resaleProdDto) {
        if(iResaleProdRepo.existsByProductCode(resaleProdDto.getProductCode())){
            throw new EntityAlreadyExistsException("Producto ya existe con codigo: " + resaleProdDto.getProductCode());
        }
        if(iResaleProdRepo.existsByProductName(resaleProdDto.getProductName())){
            throw new EntityAlreadyExistsException("Producto ya existe con el nombre: " + resaleProdDto.getProductName());
        }
        ResaleProduct resaleProduct = new ResaleProduct();
        return iResaleProdRepo.save(saveData(resaleProdDto, resaleProduct));
    }

    @Override
    public ResaleProduct getResaleProduct(int productCode) {
        return iResaleProdRepo.findByProductCode(productCode)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con codigo: "+ productCode));
    }

    @Override
    public ResaleProduct getResaleProductByProdName(String productName) {
        return iResaleProdRepo.findByProductName(productName)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con nombre: " + productName));
    }


    @Override
    public List<ResaleProduct> getAllResaleProd() {
        return iResaleProdRepo.findAll();
    }

    @Override
    @Transactional
    public ResaleProduct deleteResaleProd(int productCode) {
        ResaleProduct resaleProd = iResaleProdRepo.findByProductCode(productCode)
                .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con codigo: " + productCode));
        iResaleProdRepo.deleteByProductCode(productCode);
        return resaleProd;
    }

    @Override
    @Transactional
    public ResaleProduct editResaleProd(ResaleProdDto resaleProdDto) {
        int prodCode = resaleProdDto.getProductCode();
        ResaleProduct resaleProduct = iResaleProdRepo.findByProductCode(prodCode)
                .orElseThrow(()  -> new EntityNotFoundException("Producto no encontrado con código:" + prodCode));
        return iResaleProdRepo.save(saveData(resaleProdDto, resaleProduct));
    }

    @Transactional
    @Override
    public ResaleProduct updatestock(int productCode, int newStock) {
        ResaleProduct resaleProduct = iResaleProdRepo.findByProductCode(productCode)
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado con código: " + productCode));

        resaleProduct.setStock(newStock);
        return iResaleProdRepo.save(resaleProduct);
        }



    //------------------------------Functions------------------------------

    //Sum of variable costs
    public int getTotalCost(ResaleProdDto resaleProdDto) {
        return  resaleProdDto.getCostPrice() +
                resaleProdDto.getPackagingPrice() +
                resaleProdDto.getDeliveryPrice();
    }

    //Apply "Marking Margin (%)" to "Total Cost", then round the result  to integer, and finally round to the nearest multiple of 5.
    public int getSalePrice(ResaleProdDto resaleProdDto){
        int totalCost = getTotalCost(resaleProdDto);
        int markingMargin = resaleProdDto.getMarkingMargin();
        int roundedInt = Math.round( totalCost * (1 + markingMargin / 100.0f));
        return getRoundIntegerTo5(roundedInt);
    }

    //Diference ($) between "Sale Price" and "Total Cost".
    public int getMarginalContribution(ResaleProdDto resaleProdDto){
        int totalCost =  getTotalCost(resaleProdDto);
        int salePrice = getSalePrice(resaleProdDto);
        return salePrice - totalCost;
    }

    //
    public int getContributionMargin(ResaleProdDto resaleProdDto){
        int salePrice = getSalePrice(resaleProdDto);
        if(salePrice == 0){
            return 0;
        }
        return Math.round((getMarginalContribution(resaleProdDto) / (float)salePrice) * 100) ;
    }

    //
    public int getSalePriceWithCommission(ResaleProdDto resaleProdDto){
        int commission = resaleProdDto.getCommission();
        if(commission < 0 || commission >= 100 ){
            throw new IllegalArgumentException("Comision no debe ser menor a 0, ni mayor o igual a 100.");
        }
        float commissionFactor = 1 - resaleProdDto.getCommission()/100.0f;
        return Math.round(getSalePrice(resaleProdDto) /  commissionFactor);
    }

    public int getMarginalContribWithCommission(ResaleProdDto resaleProdDto){
        return getMarginalContribution(resaleProdDto);
    }

    public int getContribMarginWithCommission(ResaleProdDto resaleProdDto){
        return getContributionMargin(resaleProdDto);
    }

    //Round to the nearest multiple of 5.
    public int getRoundIntegerTo5(int number) {
        return Math.round(number / 5.0f) * 5;
    }

    //Get "Resale Product Dto" data, save Data into variables, assign variables to "resaleProduct variable" and finally save Data into DB.
    @Transactional
    public ResaleProduct saveData(ResaleProdDto resaleProdDto, ResaleProduct resaleProduct){
        resaleProduct.setProductCode(resaleProdDto.getProductCode());
        resaleProduct.setProductName(resaleProdDto.getProductName());
        resaleProduct.setUnitType(resaleProdDto.getUnitType());
        resaleProduct.setStock(resaleProdDto.getStock());
        resaleProduct.setExpireDate(resaleProdDto.getExpireDate());
        resaleProduct.setCostPrice(resaleProdDto.getCostPrice());
        resaleProduct.setPackagingPrice(resaleProdDto.getPackagingPrice());
        resaleProduct.setDeliveryPrice(resaleProdDto.getDeliveryPrice());
        resaleProduct.setMarkingMargin(resaleProdDto.getMarkingMargin());
        resaleProduct.setCommission(resaleProdDto.getCommission());

        int totalCost = getTotalCost(resaleProdDto);
        int salePrice = getSalePrice(resaleProdDto);
        int marginalContribution = getMarginalContribution(resaleProdDto);
        int contributionMargin = getContributionMargin(resaleProdDto);
        int salePriceWithCommission = getSalePriceWithCommission(resaleProdDto);

        resaleProduct.setTotalCost(totalCost);
        resaleProduct.setSalePrice(salePrice);
        resaleProduct.setMarginalContribution(marginalContribution);
        resaleProduct.setContributionMargin(contributionMargin);
        resaleProduct.setSalePriceWithCommission(salePriceWithCommission);
        resaleProduct.setMarginalContribWithCommission(getMarginalContribWithCommission(resaleProdDto));
        resaleProduct.setContribMarginWithCommission(getContribMarginWithCommission(resaleProdDto));
        return resaleProduct;
    }
}


