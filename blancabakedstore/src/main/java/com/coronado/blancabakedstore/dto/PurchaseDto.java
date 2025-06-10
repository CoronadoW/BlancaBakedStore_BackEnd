package com.coronado.blancabakedstore.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class PurchaseDto {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate purchaseDate;
    private String preSellerName;
    private List<PurchasedProductDto> purProdDtoList;

    @Override
    public String toString() {
        return "PurchaseDto{" +
                "purchaseDate=" + purchaseDate +
                ", preSellerName='" + preSellerName + '\'' +
                ", purProdDtoList=" + purProdDtoList +
                '}';
    }
}
