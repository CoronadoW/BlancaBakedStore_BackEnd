package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity

public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Double totalPurchaseCost;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate purchaseDate;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name= "id_preSeller")
    private PreSeller preSeller;
    @JsonManagedReference
    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL)//Cascade to persist PurchasedProducts.
    private List<PurchasedProduct> purProdList;

    @Override
    public String toString() {
        return "Purchase{" +
                "id=" + id +
                ", totalPurchaseCost=" + totalPurchaseCost +
                ", purchaseDate=" + purchaseDate +
                ", preSeller=" + preSeller +
                ", purProdList=" + purProdList +
                '}';
    }
}
