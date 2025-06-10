package com.coronado.blancabakedstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PurchasedProductDto {

    private String name;
    private String brand;
    private Double unitCost;
    private Double qty;

    @Override
    public String toString() {
        return "PurchasedProductDto{" +
                "name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", unitCost=" + unitCost +
                ", qty=" + qty +
                '}';
    }
}
