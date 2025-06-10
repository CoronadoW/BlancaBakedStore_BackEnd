package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.FinancialDto;
import com.coronado.blancabakedstore.dto.RecipeCostRequestDto;
import com.coronado.blancabakedstore.dto.RecipeDto;
import com.coronado.blancabakedstore.exceptions.EntityAlreadyExistsException;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.FinancialAnalysis;
import com.coronado.blancabakedstore.model.RecipeCost;
import com.coronado.blancabakedstore.model.RecipeDetail;
import com.coronado.blancabakedstore.repository.IRecipeCostRepository;
import com.coronado.blancabakedstore.repository.IRecipeDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class RecipeCostService implements IRecipeCostService{


    private final IRecipeCostRepository iRecipeCostRepo;
    private final RecipeDetailService recDetServ;
    private final FinancialAnalysisService finAnaServ;
    private final IRecipeDetailRepository iRecDetRepo;

    @Autowired
    public RecipeCostService(IRecipeCostRepository iRecipeCostRepo, RecipeDetailService recDetServ, FinancialAnalysisService finAnaServ, IRecipeDetailRepository iRecDetRepo) {
        this.iRecipeCostRepo = iRecipeCostRepo;
        this.recDetServ = recDetServ;
        this.finAnaServ = finAnaServ;
        this.iRecDetRepo = iRecDetRepo;
    }


    @Transactional
    public RecipeCost createRecipeCost(RecipeDto recipeDto){
        if(iRecipeCostRepo.existsByRecipeName(recipeDto.getRecipeName())){
            throw new EntityAlreadyExistsException("Recipe already exists");
        }

        // Creates "recipeDetailList" through RecipeDetailService using SupplyPerRecipeDto in RecipeDto
        List<RecipeDetail> recipeDetailList = recDetServ.createRecipeDetailList(recipeDto);
        System.out.println("Recipe detail list created satisfactory" + recipeDetailList);

        Double totalVariableCost = getTotalVariableCost(recipeDetailList);
        int markingMargin = recipeDto.getMarkingMarginDto();
        int effectivePrice = getEffectivePriceRoundedTo5(totalVariableCost, markingMargin);
        Double marginalContribution = getMarginalContribution(totalVariableCost, effectivePrice);
        int fixCostIncidence = finAnaServ.getLastFinancialAnalysis().getFixCostIncidence();
        System.out.println("Fix Cost Incidence got satisfactory: " + fixCostIncidence);
        int unitsPerRecipe = recipeDto.getUnitsPerRecipeDto();
        
        RecipeCost recipeCost = new RecipeCost();
        recipeCost.setRecipeName(recipeDto.getRecipeName());
        recipeCost.setMarkingMargin(markingMargin);
        recipeCost.setUnitsPerRecipe(unitsPerRecipe);
        recipeCost.setVariableCost(roundToTwoDecimals(totalVariableCost));

        recipeCost.setIngreVarCost(roundToTwoDecimals(getVariableCostBySuppType(recipeDetailList, "Ingrediente" )));
        recipeCost.setPackVarCost(roundToTwoDecimals(getVariableCostBySuppType(recipeDetailList, "Packaging")));
        recipeCost.setLaborVarCost(roundToTwoDecimals(getVariableCostBySuppType(recipeDetailList, "Labor")));

        recipeCost.setEffectivePrice(effectivePrice);
        recipeCost.setUnitEffectivePrice(effectivePrice / unitsPerRecipe);

        recipeCost.setMarginalContribution(roundToTwoDecimals(marginalContribution));
        recipeCost.setFixCostDistribution( roundToTwoDecimals(effectivePrice * fixCostIncidence / 100.0));
        recipeCost.setProfit(roundToTwoDecimals(marginalContribution - recipeCost.getFixCostDistribution()));

        recipeCost.setUnitTotalVarCost(roundToTwoDecimals(getTotalUnitVariableCosts(recipeDetailList)));
        System.out.println("El total de los costos unitarios variables es: " + getTotalUnitVariableCosts(recipeDetailList));

        recipeCost.setUnitIngreVarCost(roundToTwoDecimals(recipeCost.getIngreVarCost() / unitsPerRecipe));
        recipeCost.setUnitLaborVarCost(roundToTwoDecimals(recipeCost.getLaborVarCost() / unitsPerRecipe));
        recipeCost.setUnitPackVarCost(roundToTwoDecimals(recipeCost.getPackVarCost() / unitsPerRecipe));

        recipeCost.setUnitMarginalContribution(roundToTwoDecimals((double)effectivePrice/unitsPerRecipe - recipeCost.getUnitTotalVarCost()));
        recipeCost.setUnitFixCostDistribution(roundToTwoDecimals(recipeCost.getFixCostDistribution() / unitsPerRecipe));
        recipeCost.setUnitProfit(roundToTwoDecimals(recipeCost.getProfit() / unitsPerRecipe));

        recipeCost.setFixCostDistribPercent(fixCostIncidence);
        recipeCost.setVariableCostPercent(getVariableCostPercent(totalVariableCost, effectivePrice));
        recipeCost.setIngrePercent((int) (recipeCost.getIngreVarCost() / totalVariableCost * 100));
        recipeCost.setPackagingPercent((int) (recipeCost.getPackVarCost() / totalVariableCost * 100));
        recipeCost.setMarginalContribPercent((int) (recipeCost.getMarginalContribution() / effectivePrice * 100 ));
        recipeCost.setProfitPercent((int) (recipeCost.getProfit() / effectivePrice * 100));

        RecipeCost reCost = iRecipeCostRepo.save(recipeCost);

        for(RecipeDetail detail : recipeDetailList){
            detail.setRecipeCost(reCost);
        }

        iRecDetRepo.saveAll(recipeDetailList);

        recipeCost.setRecipeDetailList(recipeDetailList);

        return iRecipeCostRepo.save(reCost);
    }

    @Override
    public RecipeCost getRecipeByName(String recipeName) {
        return iRecipeCostRepo.findByRecipeName(recipeName)
                .orElseThrow(() -> new EntityNotFoundException("Recipe Cost not found"));
    }

    @Override
    public List<RecipeCost> getAllRecipeCosts() {
        return iRecipeCostRepo.findAll();
    }


    @Override
    public void deleteRecipeCost(String recipeName) {
        RecipeCost recipeCost = iRecipeCostRepo.findByRecipeName(recipeName)
               .orElseThrow(() ->  new EntityNotFoundException("Entity not found"));
        iRecipeCostRepo.delete(recipeCost);
    }

    public Double getTotalVariableCost(List<RecipeDetail> recipeDetailList){
        return recipeDetailList.stream()
                .mapToDouble(RecipeDetail :: getCostPerRecipe)
                .sum();
    }

    public Double getVariableCostBySuppType(List<RecipeDetail> recipeDetailList, String supType){
        return recipeDetailList.stream()
                .filter(recipeDetail -> recipeDetail.getSuppType().equalsIgnoreCase(supType))
                .mapToDouble(RecipeDetail :: getCostPerRecipe)
                .sum();
    }

    public int getEffectivePriceRoundedTo5(Double totalVariableCost, int markingMargin ){
       int effPrice = (int)(totalVariableCost * (1 + markingMargin / 100.0));
       return getRoundIntegerTo5(effPrice);
    }

    public int getRoundIntegerTo5(int number){
        return Math.round(number/5.0f) * 5;
    }

    public Double getMarginalContribution(Double totalVariableCost, int effectivePrice){
        return effectivePrice - totalVariableCost;
    }

    public int getVariableCostPercent(Double totalVariableCost , int effectivePrice) {
        if(totalVariableCost == null || effectivePrice == 0){
            throw new IllegalArgumentException("Costo variable total no puede ser nulo y Precio efectivo no puede ser 0");
        }
        return (int) (Math.round(totalVariableCost /  effectivePrice * 100));
    }

    public Double getTotalUnitVariableCosts(List<RecipeDetail> recipeDetailList){
        return recipeDetailList.stream()
                .mapToDouble(RecipeDetail :: getUnitCostPerSupp)
                .sum();
    }

    public Double roundToTwoDecimals(Double value){
        if(value == null) return null;
        return BigDecimal.valueOf(value)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }





}
