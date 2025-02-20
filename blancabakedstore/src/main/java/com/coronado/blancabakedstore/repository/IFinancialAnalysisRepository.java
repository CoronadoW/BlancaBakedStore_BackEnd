package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.FinancialAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IFinancialAnalysisRepository extends JpaRepository <FinancialAnalysis, Long>{
}
