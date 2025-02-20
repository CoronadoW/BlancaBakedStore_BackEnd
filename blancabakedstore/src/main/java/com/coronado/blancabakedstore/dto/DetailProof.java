package com.coronado.blancabakedstore.dto;

import com.coronado.blancabakedstore.model.RecipeCost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class DetailProof {

    private String suppName;
    private String suppType;
    private Double qty;
    private int waste;
    private Double cost;
    private Double unitCost;
    private RecipeCost recipeCost;
}
