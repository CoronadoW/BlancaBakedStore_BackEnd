package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.FinancialDto;
import com.coronado.blancabakedstore.dto.RecipeCostRequestDto;
import com.coronado.blancabakedstore.dto.RecipeDto;
import com.coronado.blancabakedstore.model.FinancialAnalysis;
import com.coronado.blancabakedstore.model.RecipeCost;
import com.coronado.blancabakedstore.model.RecipeDetail;
import com.coronado.blancabakedstore.repository.IRecipeCostRepository;
import com.coronado.blancabakedstore.repository.IRecipeDetailRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    //public RecipeCost createRecipeCost(RecipeDto recipeDto, List<RecipeDetail> recipeDetailList, FinancialDto financialDto){
    //public RecipeCost createRecipeCost(RecipeDto recipeDto, FinancialDto financialDto){
    //public RecipeCost createRecipeCost(RecipeCostRequestDto recipeCostRequestDto){
    @Transactional
    public RecipeCost createRecipeCost(RecipeDto recipeDto, Long id){

        //Get RecipeDto and FinancialDto from RecipeCostRequestDto
            //RecipeDto recipeDto = recipeCostRequestDto.getRecipeDto();
            //FinancialDto financialDto = recipeCostRequestDto.getFinancialDto();

        // Creates "recipeDetailList" through RecipeDetailService using SupplyPerRecipeDto in RecipeDto
        List<RecipeDetail> recipeDetailList = recDetServ.createRecipeDetailList(recipeDto);
        System.out.println("Recipe detail list created satisfactory" + recipeDetailList);

        Double totalVariableCost = getTotalVariableCost(recipeDetailList);
        int markingMargin = recipeDto.getMarkingMarginDto();
        int effectivePrice = getEffectivePriceRoundedTo5(totalVariableCost, markingMargin);
        Double marginalContribution = getMarginalContribution(totalVariableCost, effectivePrice);

        //int fixCostIncidence = finAnaServ.calculateFixCostIncidence(financialDto);
        int fixCostIncidence = finAnaServ.getFixCostIncidenceById(id);
        System.out.println("Fix Cost Incidence created satisfactory: " + fixCostIncidence);
        
        RecipeCost recipeCost = new RecipeCost();
        recipeCost.setRecipeName(recipeDto.getRecipeName());
        recipeCost.setMarkingMargin(recipeDto.getMarkingMarginDto());
        recipeCost.setUnitsPerRecip(recipeDto.getUnitsPerRecipeDto());
        //recipeCost.setRecipeDetailList(recipeDetailList);
        recipeCost.setVariableCost(totalVariableCost);
        recipeCost.setIngreVarCost(getVariableCostBySuppType(recipeDetailList, "ingredient" ));
        recipeCost.setPackVarCost(getVariableCostBySuppType(recipeDetailList, "Packaging"));
        recipeCost.setLaborVarCost(getVariableCostBySuppType(recipeDetailList, "Labor"));
        recipeCost.setEffectivePrice(effectivePrice);
        recipeCost.setMarginalContribution(marginalContribution);
        recipeCost.setFixCostDistribution( effectivePrice * fixCostIncidence / 100.0);
        recipeCost.setProfit(marginalContribution - recipeCost.getFixCostDistribution());
        recipeCost.setFixCostDistribPercent(fixCostIncidence);
        recipeCost.setVariableCostPercent(getVariableCostPercent(totalVariableCost, effectivePrice));
        recipeCost.setIngrePercent((int) (recipeCost.getIngreVarCost() / effectivePrice * 100));
        recipeCost.setPackagingPercent((int) (recipeCost.getPackVarCost() / effectivePrice * 100));
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

}
