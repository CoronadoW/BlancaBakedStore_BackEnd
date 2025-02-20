package com.coronado.blancabakedstore.dto;

import com.coronado.blancabakedstore.model.RecipeDetail;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter

public class RecipeCostRequestDto {
    private RecipeDto recipeDto;
    private FinancialDto financialDto;
    //private Long financialId; quiza deberia ir solo id y no financialId
}
