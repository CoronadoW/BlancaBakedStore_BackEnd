package com.coronado.blancabakedstore.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    private String preSellerPhone;
    private String address;
    private String email;

    @JsonManagedReference
    @OneToMany(mappedBy = "preSeller")
    private List<Purchase> purchaseList;

    @Override
    public String toString() {
        return "PreSeller{" +
                "id=" + id +
                ", preSellerName='" + preSellerName + '\'' +
                ", preSellerBrand='" + preSellerBrand + '\'' +
                ", phone='" + preSellerPhone + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", purchaseCount=" + (purchaseList !=null ? purchaseList.size() : 0) +
                '}';
    }
}
