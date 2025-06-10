package com.coronado.blancabakedstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CashBalanceDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private String shift; //For shift of employees
    private String employeeName; /*????*/
    private Double openingCashBalance;
    private Double openingCashOnHand;
    private Double cashSales;
    private Double cardSales;
    private Double transferSales;
    private Double qrSales;
    private Double systemTotalSales;
    private Double cashPayments;
    private Double otherPayments;
    private String observations;
}
