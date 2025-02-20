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
public class Supply {

    //Model for "supplies" that will be used in production by the store to fabricate many products(recipes costs and sub recipes costs)
    //This model allows the imput of costs of supplies to calculate severals values by another Entity (" Costs by Recipe - RecipeCost")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int supplyCode;
    private String supplyName;
    private int qtyPerUnit;
    private String unit;
    private Double cost;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate buyDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate expireDate;
    private String supplyType;

    @Override
    public String toString() {
        return "Supply{" +
                "id=" + id +
                ", supplyCode=" + supplyCode +
                ", supplyName='" + supplyName + '\'' +
                ", qtyPerUnit=" + qtyPerUnit +
                ", unit='" + unit + '\'' +
                ", cost=" + cost +
                ", buyDate=" + buyDate +
                ", expireDate=" + expireDate +
                ", supplyType='" + supplyType + '\'' +
                '}';
    }
}
