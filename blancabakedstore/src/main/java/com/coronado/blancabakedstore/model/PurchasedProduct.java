package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class PurchasedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String purProdName;
    private String purProdBrand;
    private Double purProdUnitCost;
    private Double purProdQty;
    private Double totalCost;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name= "id_purchase")
    private Purchase purchase;



    @Override
    public String toString() {
        return "PurchasedProduct{" +
                "id=" + id +
                ", purProdName='" + purProdName + '\'' +
                ", purProdBrand='" + purProdBrand + '\'' +
                ", purProdCost='" + purProdUnitCost + '\'' +
                ", purProdQty='" + purProdQty + '\'' +
                ", totalCost='" + totalCost + '\'' +
                ", purchaseId=" + ( purchase !=null ? purchase.getId() : null ) +
                '}';
    }
}
