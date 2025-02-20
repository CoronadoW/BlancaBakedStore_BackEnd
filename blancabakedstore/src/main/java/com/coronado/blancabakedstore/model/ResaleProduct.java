package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class ResaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int productCode;        //Insert
    private String productName;     //Insert
    private String unitType;        //Insert
    private int stock;              //Insert
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expireDate;   //Insert
    private int costPrice;          //Insert
    private int packagingPrice;     //Insert
    private int deliveryPrice;      //Insert
    private int markingMargin;      //Insert
    private int commission;         //Insert
    private int totalCost;                      //F (Function)
    private int salePrice;                      //F
    private int marginalContribution;           //F
    private int contributionMargin;             //F
    private int salePriceWithCommission;        //F
    private int marginalContribWithCommission;  //F
    private int contribMarginWithCommission;    //F

    @Override
    public String toString() {
        return "ResaleProduct{" +
                "id=" + id +
                ", productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", unitType='" + unitType + '\'' +
                ", stock=" + stock +
                ", expireDate=" + expireDate +
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
