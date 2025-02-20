package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.CashBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ICashBalanceRepository extends JpaRepository<CashBalance, Long> {

    List<CashBalance> findByDate(LocalDate date);

}
