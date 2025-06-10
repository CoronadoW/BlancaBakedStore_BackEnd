package com.coronado.blancabakedstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
//@AllArgsConstructor
@Setter
@Getter
public class ResaleProdWithNearestDateDto {

    private int productCode;
    private String productName;
    private String unitType;
    private int stock;

    private int costPrice;
    private int packagingPrice;
    private int deliveryPrice;
    private int markingMargin;

    private int commission;
    private int totalCost;
    private int salePrice;

    private int marginalContribution;
    private int contributionMargin;

    private int salePriceWithCommission;
    private int marginalContribWithCommission;
    private int contribMarginWithCommission;
   // private LocalDate buyDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate nearestExpireDate;

    private int qty;


    // Constructor, specific for JPQL, IResaleProduct Repository methods:
    // Optional<ResaleProdWithNearestDateDto> findProductWithNearestExpireDateByCode (@Param("productCode") int productCode);
    // Optional<ResaleProdWithNearestDateDto> findProductWithNearestExpireDateByName(@Param("productName") String productName);
    public ResaleProdWithNearestDateDto(
            int productCode,
            String productName,
            String unitType,
            int stock,
            int costPrice,
            int packagingPrice,
            int deliveryPrice,
            int markingMargin,
            int commission,
            int totalCost,
            int salePrice,
            int marginalContribution,
            int contributionMargin,
            int salePriceWithCommission,
            int marginalContribWithCommission,
            int contribMarginWithCommission,
            LocalDate nearestExpireDate,
            int qty
    ) {
        this.productCode = productCode;
        this.productName = productName;
        this.unitType = unitType;
        this.stock = stock;
        this.costPrice = costPrice;
        this.packagingPrice = packagingPrice;
        this.deliveryPrice = deliveryPrice;
        this.markingMargin = markingMargin;
        this.commission = commission;
        this.totalCost = totalCost;
        this.salePrice = salePrice;
        this.marginalContribution = marginalContribution;
        this.contributionMargin = contributionMargin;
        this.salePriceWithCommission = salePriceWithCommission;
        this.marginalContribWithCommission = marginalContribWithCommission;
        this.contribMarginWithCommission = contribMarginWithCommission;
        this.nearestExpireDate = nearestExpireDate;
        this.qty = qty;
    }


}
