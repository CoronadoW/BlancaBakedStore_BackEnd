package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.FinancialDto;
import com.coronado.blancabakedstore.dto.RecipeCostRequestDto;
import com.coronado.blancabakedstore.dto.RecipeDto;
import com.coronado.blancabakedstore.model.RecipeCost;
import com.coronado.blancabakedstore.model.RecipeDetail;

import java.util.List;

public interface IRecipeCostService {

    RecipeCost createRecipeCost(RecipeDto recipeDto);

    RecipeCost getRecipeByName(String recipeName);

    List<RecipeCost> getAllRecipeCosts();

    void deleteRecipeCost(String recipeName);

    //RecipeCost createRecipeCost(RecipeDto recipeDto, FinancialDto financialDto);
    //RecipeCost createRecipeCost(RecipeCostRequestDto recipeCostRequestDto);
}
