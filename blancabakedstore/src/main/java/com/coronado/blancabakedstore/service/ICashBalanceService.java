package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.CashBalanceDto;
import com.coronado.blancabakedstore.model.CashBalance;

import java.time.LocalDate;
import java.util.List;

public interface ICashBalanceService {

    CashBalance createCashBalance(CashBalanceDto cashBalanceDto);

    CashBalance getCashBalance(Long id);

    List<CashBalance> getAllCashBalance();

    List<CashBalance> getCashBalanceByDate(LocalDate date);

    CashBalance deleteCashBalance(Long id);

    CashBalance editCashBalance(CashBalanceDto cashBalanceDto, Long id);

}
