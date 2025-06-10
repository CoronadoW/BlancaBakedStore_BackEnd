package com.coronado.blancabakedstore.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class FinancialAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double normalFixCost;
    private Double normalSaleAverage;
    private int fixCostIncidence;

}
