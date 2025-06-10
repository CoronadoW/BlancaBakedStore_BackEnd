package com.coronado.blancabakedstore.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
//Dto gets data to find CashBalance through its date and shift.
public class BalanceDto {

    private LocalDate balanceDateDto;
    private String balanceShiftDto;
}
