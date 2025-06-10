package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.FinancialAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFinancialAnalysisRepository extends JpaRepository <FinancialAnalysis, Long>{

    Optional<FinancialAnalysis> findTopByOrderByIdDesc();

}
