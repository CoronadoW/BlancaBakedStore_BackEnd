package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class RecipeDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String suppName;
    private String suppType;
    private Double qtyUtilized;
    private int waste; //%
    private Double costPerRecipe;
    private Double unitCostPerSupp;
    @ManyToOne
    @JoinColumn(name = "fk_idRecipeCost",  nullable = true)
    @JsonBackReference //Instead JsonIgnore to avoid serialization and keep List available
    private RecipeCost recipeCost;

    @Override
    public String toString() {
        return "RecipeDetail{" +
                "id=" + id +
                ", suppName='" + suppName + '\'' +
                ", suppType='" + suppType + '\'' +
                ", qtyUtilized=" + qtyUtilized +
                ", waste=" + waste +
                ", costPerRecipe=" + costPerRecipe +
                ", unitCostPerSupp=" + unitCostPerSupp +
                //Null added to avoid StackOverFlow error because of recursive
                ", recipeCost=" +  (recipeCost != null ? recipeCost.getId() : "null") +
                '}';

    }
}
