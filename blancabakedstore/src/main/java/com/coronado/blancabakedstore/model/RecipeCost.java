package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class RecipeCost {
    //Final entity wich will save all values for "Recipe costs" with all the final values.
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String recipeName;
    private int markingMargin;
    private int unitsPerRecipe;

    @OneToMany(mappedBy = "recipeCost", cascade = CascadeType.ALL) //, orphanRemoval = true
    @JsonManagedReference //Instead JsonIgnore to avoid serialization and keep available the list in the RecipeCost
    private List<RecipeDetail> recipeDetailList = new ArrayList<>();

    private Double variableCost;
    private Double ingreVarCost;
    private Double packVarCost;
    private Double laborVarCost;
    private int effectivePrice;
    private int unitEffectivePrice;
    private Double marginalContribution;
    private Double fixCostDistribution;
    private Double profit;
    private Double unitTotalVarCost;
    private Double unitIngreVarCost;
    private Double unitLaborVarCost;
    private Double unitPackVarCost;
    private Double unitMarginalContribution;
    private Double unitFixCostDistribution;
    private Double unitProfit;
    private int variableCostPercent;
    private int ingrePercent;
    private int variableLaborPercent;
    private int packagingPercent;
    private int marginalContribPercent;
    private int fixCostDistribPercent;
    private int profitPercent;



    @Override
    public String toString() {
        return "RecipeCost{" +
                "id=" + id +
                ", recipeName='" + recipeName + '\'' +
                ", markingMargin=" + markingMargin +
                ", unitsPerRecip=" + unitsPerRecipe +
                //Null added to avoid StackOverFlow error because of recursive
                ", recipeDetailList=" + (recipeDetailList != null ? recipeDetailList.size() : "null") +
                ", variableCost=" + variableCost +
                ", ingreVarCost=" + ingreVarCost +
                ", packVarCost=" + packVarCost +
                ", laborVarCost=" + laborVarCost +
                ", effectivePrice=" + effectivePrice +
                ", unitEffectivePrice=" + unitEffectivePrice +
                ", marginalContribution=" + marginalContribution +
                ", fixCostDistribution=" + fixCostDistribution +
                ", profit=" + profit +
                ", unitTotalVarCost=" + unitTotalVarCost +
                ", unitIngreVarCost=" + unitIngreVarCost +
                ", unitLaborVarCost=" + unitLaborVarCost +
                ", unitPackVarCost=" + unitPackVarCost +
                ", unitMarginalContribution=" + unitMarginalContribution +
                ", unitFixCostDistribution=" + unitFixCostDistribution +
                ", unitProfit=" + unitProfit +
                ", variableCostPercent=" + variableCostPercent +
                ", ingrePercent=" + ingrePercent +
                ", variableLaborPercent=" + variableLaborPercent +
                ", packagingPercent=" + packagingPercent +
                ", marginalContribPercent=" + marginalContribPercent +
                ", fixCostDistribPercent=" + fixCostDistribPercent +
                ", profitPercent=" + profitPercent +
                '}';
    }
}
