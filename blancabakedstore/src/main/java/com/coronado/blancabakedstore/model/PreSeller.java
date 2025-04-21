package com.coronado.blancabakedstore.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class PreSeller {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String preSellerName;
    private String preSellerBrand;
    private int phone;
    private String address;
    @OneToMany(mappedBy = "preSeller")
    private List<PurchasedProduct> purchasedProductList;

}
