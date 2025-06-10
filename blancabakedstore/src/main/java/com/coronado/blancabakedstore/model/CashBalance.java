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
public class CashBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;
    private String shift; //For shift of employees
    private String employeeName;
    private Double openingCashBalance;
    private Double openingCashOnHand;
    private Double cashSales;
    private Double cardSales;
    private Double transferSales;
    private Double qrSales;
    private Double systemTotalSales;
    private Double totalSales;  //Function
    private Double control;     //Function  TotalSales - SystemTotalSales
    private Double cashPayments;
    private Double otherPayments;
    private Double totalPayments; //Function cashPayments + otherPayments
    private Double endingCashBalance;
    private String observations;

    @Override
    public String toString() {
        return "CashBalance{" +
                "id=" + id +
                ", date=" + date +
                ", shift='" + shift + '\'' +
                ", employeeName='" + employeeName + '\'' +
                ", openingCashBalance=" + openingCashBalance +
                ", openingCashOnHand=" + openingCashOnHand +
                ", cashSales=" + cashSales +
                ", cardSales=" + cardSales +
                ", transferSales=" + transferSales +
                ", qrSales=" + qrSales +
                ", systemTotalSales=" + systemTotalSales +
                ", totalSales=" + totalSales +
                ", control=" + control +
                ", cashPayments=" + cashPayments +
                ", otherPayments=" + otherPayments +
                ", totalPayments=" + totalPayments +
                ", observations='" + observations + '\'' +
                '}';
    }
}
