package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class ResaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int productCode;
    private String productName;
    private String unitType;
    private int stock;
    private int costPrice;
    private int packagingPrice;
    private int deliveryPrice;
    private int markingMargin;
    private int commission;
    private int totalCost;                      //F (Function)
    private int salePrice;                      //F
    private int marginalContribution;           //F
    private int contributionMargin;             //F
    private int salePriceWithCommission;        //F
    private int marginalContribWithCommission;  //F
    private int contribMarginWithCommission;    //F
    @OneToMany(mappedBy = "resaleProduct", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Expiration> expirations = new ArrayList<>();

    @Override
    public String toString() {
        return "ResaleProduct{" +
                "id=" + id +
                ", productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", unitType='" + unitType + '\'' +
                ", stock=" + stock +
                ", costPrice=" + costPrice +
                ", packagingPrice=" + packagingPrice +
                ", deliveryPrice=" + deliveryPrice +
                ", markingMargin=" + markingMargin +
                ", commission=" + commission +
                ", totalCost=" + totalCost +
                ", salePrice=" + salePrice +
                ", marginalContribution=" + marginalContribution +
                ", contributionMargin=" + contributionMargin +
                ", salePriceWithCommission=" + salePriceWithCommission +
                ", marginalContribWithCommission=" + marginalContribWithCommission +
                ", contribMarginWithCommission=" + contribMarginWithCommission +
                '}';
    }
}
