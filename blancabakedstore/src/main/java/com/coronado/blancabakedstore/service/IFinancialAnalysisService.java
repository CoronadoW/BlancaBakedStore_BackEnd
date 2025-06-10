package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.FinancialDto;
import com.coronado.blancabakedstore.model.FinancialAnalysis;

import java.util.List;

public interface IFinancialAnalysisService {

    FinancialAnalysis createFinancialAnalysis(FinancialDto financialDto);

    FinancialAnalysis getLastFinancialAnalysis();

    int calculateFixCostIncidence(FinancialDto financialDto);

    FinancialAnalysis getFinancialAnalysisById(Long id);

    int getFixCostIncidenceById(Long id);

    List<FinancialAnalysis> getAllFinancialAnalysis();

    String  deleteFinancialAnalysis(Long id);

    FinancialAnalysis editFinancialAnalysis(FinancialDto financialDto, Long id);
}
