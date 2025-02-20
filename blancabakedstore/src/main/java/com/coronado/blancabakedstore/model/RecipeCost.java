package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private int unitsPerRecip;

    @OneToMany(mappedBy = "recipeCost", cascade = CascadeType.ALL) //, orphanRemoval = true
    @JsonIgnore
    private List<RecipeDetail> recipeDetailList = new ArrayList<>();

    private Double variableCost;
    private Double ingreVarCost;
    private Double packVarCost;
    private Double laborVarCost;
    private int effectivePrice;
    private Double marginalContribution;
    private Double fixCostDistribution;
    private Double profit;
    private int variableCostPercent;
    private int ingrePercent;
    private int packagingPercent;
    private int variableLaborPercent;
    private int marginalContribPercent;
    private int fixCostDistribPercent;
    private int profitPercent;

    @Override
    public String toString() {
        return "RecipeCost{" +
                "id=" + id +
                ", recipeName='" + recipeName + '\'' +
                ", markingMargin=" + markingMargin +
                ", unitsPerRecip=" + unitsPerRecip +
                //Null added to avoid StackOverFlow error because of recursive
                ", recipeDetailList=" + (recipeDetailList != null ? recipeDetailList.size() : "null") +
                ", variableCost=" + variableCost +
                ", ingreVarCost=" + ingreVarCost +
                ", packVarCost=" + packVarCost +
                ", laborVarCost=" + laborVarCost +
                ", effectivePrice=" + effectivePrice +
                ", marginalContribution=" + marginalContribution +
                ", fixCostDistribution=" + fixCostDistribution +
                ", profit=" + profit +
                ", variableCostPercent=" + variableCostPercent +
                ", ingrePercent=" + ingrePercent +
                ", packagingPercent=" + packagingPercent +
                ", variableLaborPercent=" + variableLaborPercent +
                ", marginalContribPercent=" + marginalContribPercent +
                ", fixCostDistribPercent=" + fixCostDistribPercent +
                ", profitPercent=" + profitPercent +
                '}';
    }
}
