package com.coronado.blancabakedstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

// RecipeDto receives recipeName,
public class RecipeDto {
    private String recipeName;
    private int markingMarginDto;
    private int unitsPerRecipeDto;
    private List<SupplyPerRecipeDto> suppPerRecipeDtoList;
}
