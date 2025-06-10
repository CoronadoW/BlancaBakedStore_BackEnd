package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.CashBalance;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ICashBalanceRepository extends JpaRepository<CashBalance, Long> {

   List<CashBalance> findAllByOrderByDateDesc();

   Optional<CashBalance> findByDateAndShift(LocalDate date, String shift);

   List<CashBalance> findByDateOrderByDateDesc(LocalDate date);

   List<CashBalance> findByShiftOrderByDateDesc(String shift);

   boolean existsByDateAndShift(LocalDate date, String shift);
}
