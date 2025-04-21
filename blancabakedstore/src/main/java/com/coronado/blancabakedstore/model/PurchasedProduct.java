package com.coronado.blancabakedstore.model;

import jakarta.persistence.*;
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
public class PurchasedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String purProdName;
    private String purProdBrand;
    private int cost;
    private LocalDate purchasedDate;
    @ManyToOne
    private PreSeller preSeller;


}
