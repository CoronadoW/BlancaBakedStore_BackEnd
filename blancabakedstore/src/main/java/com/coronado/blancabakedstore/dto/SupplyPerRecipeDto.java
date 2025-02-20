package com.coronado.blancabakedstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

//Supply Per Recipe Dto receives "supply name", "qty utilized per recipe" and "waste percentage of supply per recipe" to calculate recipe cost per supply and unit cost per supply.
public class SupplyPerRecipeDto {

    private String suppNameDto;
    private Double utilizedDto;
    private int wasteDto; // %
}
