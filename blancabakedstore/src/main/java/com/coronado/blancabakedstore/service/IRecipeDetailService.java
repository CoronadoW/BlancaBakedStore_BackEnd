package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.DetailProof;
import com.coronado.blancabakedstore.dto.RecipeDto;
import com.coronado.blancabakedstore.model.RecipeDetail;

import java.util.List;

public interface IRecipeDetailService {

    List<RecipeDetail> createRecipeDetailList(RecipeDto recipeDto);

    List<RecipeDetail>  getAllRecipeDetail();

}
