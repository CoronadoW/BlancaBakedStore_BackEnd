package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.ExpirationDto;
import com.coronado.blancabakedstore.dto.ResaleProdDto;
import com.coronado.blancabakedstore.dto.ResaleProdWithNearestDateDto;
import com.coronado.blancabakedstore.exceptions.EntityAlreadyExistsException;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.Expiration;
import com.coronado.blancabakedstore.model.ResaleProduct;
import com.coronado.blancabakedstore.repository.IResaleProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class ResaleProductService implements IResaleProductService{

    private final IResaleProductRepository iResaleProdRepo;
    private final ExpirationService expServ;
    @Autowired
    public ResaleProductService(IResaleProductRepository iResaleProdRepo, ExpirationService expServ) {
        this.iResaleProdRepo = iResaleProdRepo;
        this.expServ = expServ;
    }

    @Override
    @Transactional
    //Method to create a ResaleProduct with a initial Expiration, added to a list of Expirations associated to the ResaleProduct
    //The method response is a ResaleProdWithNearestDateDto, which has the initial expiration date that was used to create the ResaleProduct.
    public ResaleProdWithNearestDateDto createResaleProd(ResaleProdDto resaleProdDto) {
        if(iResaleProdRepo.existsByProductCode(resaleProdDto.getProductCode())){
            throw new EntityAlreadyExistsException("Producto ya existe con codigo: " + resaleProdDto.getProductCode());
        }
        if(iResaleProdRepo.existsByProductName(resaleProdDto.getProductName())){
            throw new EntityAlreadyExistsException("Producto ya existe con el nombre: " + resaleProdDto.getProductName());
        }

        ResaleProduct resaleProduct = new ResaleProduct();

        Expiration expiration = expServ.createExpiration(resaleProdDto.getExpDto());

        expiration.setResaleProduct(resaleProduct);
        expiration.setQty(resaleProdDto.getStock());

        List<Expiration> expirationsList = new ArrayList<>();
        expirationsList.add((expiration));

        resaleProduct.setExpirations(expirationsList);
        iResaleProdRepo.save(saveData(resaleProdDto, resaleProduct));

        ResaleProdWithNearestDateDto resProdNearestDate = new ResaleProdWithNearestDateDto();

        resProdNearestDate.setProductCode(resaleProduct.getProductCode());
        resProdNearestDate.setProductName(resaleProduct.getProductName());
        resProdNearestDate.setUnitType(resaleProduct.getUnitType());
        resProdNearestDate.setStock(resaleProduct.getStock());

        resProdNearestDate.setCostPrice(resaleProduct.getCostPrice());
        resProdNearestDate.setPackagingPrice(resaleProduct.getPackagingPrice());
        resProdNearestDate.setDeliveryPrice(resaleProduct.getDeliveryPrice());
        resProdNearestDate.setMarkingMargin(resaleProduct.getMarkingMargin());

        resProdNearestDate.setCommission(resaleProduct.getCommission());
        resProdNearestDate.setTotalCost(resaleProduct.getTotalCost());
        resProdNearestDate.setSalePrice(resaleProduct.getSalePrice());

        resProdNearestDate.setMarginalContribution(resaleProduct.getMarginalContribution());
        resProdNearestDate.setContributionMargin(resaleProduct.getContributionMargin());

        resProdNearestDate.setSalePriceWithCommission(resaleProduct.getSalePriceWithCommission());
        resProdNearestDate.setMarginalContribWithCommission(resaleProduct.getMarginalContribWithCommission());
        resProdNearestDate.setContribMarginWithCommission(resaleProduct.getContribMarginWithCommission());

        resProdNearestDate.setNearestExpireDate(resaleProdDto.getExpDto().getExpireDate());
        return resProdNearestDate;
    }

    //Method to get all ResaleProduct with its nearest expiration date, it will send a response like a ResaleProdWithNearestDateDto, thad include the expiration date used to be created.
    @Override
    public List<ResaleProdWithNearestDateDto> getAllWithNearestExpDate() {
        return iResaleProdRepo.findAll().stream().map(prod -> {

            //Find nearest expiration date for each ResaleProduct
            /*LocalDate nearest = prod.getExpirations().stream()
                    .map(Expiration::getExpireDate)
                    .filter(Objects::nonNull)
                    .filter(date -> !date.isBefore(LocalDate.now())) // opcional: excluir fechas ya vencidas
                    .min(LocalDate::compareTo)
                    .orElse(null);  */

            //Find the nearest expiration(not expired)
            Expiration nearestExp = prod.getExpirations().stream()
                    .filter(exp-> exp.getExpireDate() != null)
                    .filter( exp -> !exp.getExpireDate().isBefore(LocalDate.now())) //Exclude already expired dates.
                    .min(Comparator.comparing(Expiration::getExpireDate))
                    .orElse(null);

            LocalDate nearest = (nearestExp != null) ? nearestExp.getExpireDate() : null;
            int qty = (nearestExp != null) ? nearestExp.getQty() : 0;


            return new ResaleProdWithNearestDateDto(
                    prod.getProductCode(),
                    prod.getProductName(),
                    prod.getUnitType(),
                    prod.getStock(),
                    prod.getCostPrice(),
                    prod.getPackagingPrice(),
                    prod.getDeliveryPrice(),
                    prod.getMarkingMargin(),
                    prod.getCommission(),
                    prod.getTotalCost(),
                    prod.getSalePrice(),
                    prod.getMarginalContribution(),
                    prod.getContributionMargin(),
                    prod.getSalePriceWithCommission(),
                    prod.getMarginalContribWithCommission(),
                    prod.getContribMarginWithCommission(),
                    nearest,
                    qty
            );
        }).toList();
    }

    //Method to get Resale Product with only its nearest expiration date, without all its associated Expirations, because of the JPQL method in ResaleProductRepository.
    @Override
    public ResaleProdWithNearestDateDto getResaleProdWNearestExpByCode(int productCode) {
       return iResaleProdRepo.findProductWithNearestExpireDateByCode(productCode)
               .orElseThrow(()-> new EntityNotFoundException("Resale Product not found"));
    }

    @Override
    public ResaleProdWithNearestDateDto getResaleProdWNearestExpByName(String productName) {
        return iResaleProdRepo.findProductWithNearestExpireDateByName(productName)
                .orElseThrow(()-> new EntityNotFoundException("Resale Product not found"));
    }


    /*------------------ Logic with Resale Product only , without nearest expiration date --------------------------------------*/

    //Method to get a ResaleProduct by its product code.
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


    /*------------------------------Functions------------------------------*/

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

    //Difference ($) between "Sale Price" and "Total Cost".
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


