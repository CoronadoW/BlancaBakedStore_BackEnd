package com.coronado.blancabakedstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResaleProdDto {

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

}

