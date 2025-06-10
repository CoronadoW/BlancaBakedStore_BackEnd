package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.FinancialDto;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.FinancialAnalysis;
import com.coronado.blancabakedstore.repository.IFinancialAnalysisRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FinancialAnalysisService implements IFinancialAnalysisService {

    public final IFinancialAnalysisRepository iFinAnaRepo;
    @Autowired
    public FinancialAnalysisService(IFinancialAnalysisRepository iFinAnaRepo) {
        this.iFinAnaRepo = iFinAnaRepo;
    }

    @Transactional
    public FinancialAnalysis createFinancialAnalysis(FinancialDto financialDto) {
        FinancialAnalysis financialAnalysis = new FinancialAnalysis();

        financialAnalysis.setNormalFixCost(financialDto.getNormalFixCostDto());
        financialAnalysis.setNormalSaleAverage(financialDto.getNormalSaleAverageDto());
        financialAnalysis.setFixCostIncidence(calculateFixCostIncidence(financialDto));

        return iFinAnaRepo.save(financialAnalysis);
       // return "Incidencia de costo fijos obtenido con exito : " + financialAnalysis.getFixCostIncidence() + "%";
    }

    public FinancialAnalysis getLastFinancialAnalysis(){
        return iFinAnaRepo.findTopByOrderByIdDesc()
                .orElseThrow(() -> new EntityNotFoundException("Incidencia de costos fijos no encontrado"));
    }

    public int calculateFixCostIncidence(FinancialDto financialDto) {
        if (financialDto.getNormalFixCostDto() == null || financialDto.getNormalSaleAverageDto() == null) {
            throw new IllegalArgumentException("Los valores de costos fijos y ventas promedio no pueden ser nulos");
        }
        return (int) ((financialDto.getNormalFixCostDto() / financialDto.getNormalSaleAverageDto()) * 100);
    }

    public FinancialAnalysis getFinancialAnalysisById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del análisis financiero no puede ser nulo");
        }
        return iFinAnaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analisis financiero no encontrado con el id: " + id));
    }

    public int getFixCostIncidenceById(Long id){
        if (id == null) {
            throw new IllegalArgumentException("El ID del análisis financiero no puede ser nulo");
        }
        FinancialAnalysis finAn = iFinAnaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analisis financiero no encontrado con el id: " + id));
        return finAn.getFixCostIncidence();
    }

    public String  deleteFinancialAnalysis(Long id){
        if(iFinAnaRepo.existsById(id)){
            iFinAnaRepo.deleteById(id);
        }
        return "Analisis financiero elminado con exito";
    }

    public List<FinancialAnalysis> getAllFinancialAnalysis(){
        return iFinAnaRepo.findAll();
    }

    @Transactional
    public FinancialAnalysis editFinancialAnalysis(FinancialDto financialDto, Long id){
        FinancialAnalysis finAna = iFinAnaRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Analisis financiero no encontrado con id: " + id));
        finAna.setNormalFixCost(financialDto.getNormalFixCostDto());
        finAna.setNormalSaleAverage(financialDto.getNormalSaleAverageDto());
        finAna.setFixCostIncidence(calculateFixCostIncidence(financialDto));
        return iFinAnaRepo.save(finAna);
    }

}