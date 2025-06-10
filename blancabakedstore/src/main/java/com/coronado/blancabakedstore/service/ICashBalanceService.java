package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.BalanceDto;
import com.coronado.blancabakedstore.dto.CashBalanceDto;
import com.coronado.blancabakedstore.model.CashBalance;

import java.time.LocalDate;
import java.util.List;

public interface ICashBalanceService {

    CashBalance createCashBalance(CashBalanceDto cashBalanceDto);

    CashBalance getCashBalance(Long id);

    List<CashBalance> getAllCashBalance();

    CashBalance getCashBalanceByDateAndShift(BalanceDto balanceDto);

    List<CashBalance> getCashBalanceByDate(LocalDate date);

    List<CashBalance> getCashBalanceByShift(String shift);

    CashBalance deleteCashBalance(Long id);

    CashBalance editCashBalance(CashBalanceDto cashBalanceDto, Long id);



}
