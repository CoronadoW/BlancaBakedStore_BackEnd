package com.coronado.blancabakedstore.controller;

import com.coronado.blancabakedstore.dto.FinancialDto;
import com.coronado.blancabakedstore.model.FinancialAnalysis;
import com.coronado.blancabakedstore.service.IFinancialAnalysisService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/financialAnalysis")
public class FinancialAnalysisController {

    private final IFinancialAnalysisService iFinAnaServ;
    @Autowired
    public FinancialAnalysisController(IFinancialAnalysisService iFinAnaServ) {
        this.iFinAnaServ = iFinAnaServ;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createFinancialAnalysis(@RequestBody FinancialDto financialDto){
        return new ResponseEntity<>(iFinAnaServ.createFinancialAnalysis(financialDto), HttpStatus.CREATED);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<FinancialAnalysis> getFinancialAnalysis(@PathVariable Long id){
        return new ResponseEntity<>(iFinAnaServ.getFinancialAnalysisById(id), HttpStatus.OK);
    }

    @GetMapping("/getCostIncidenceById/{id}")
    public ResponseEntity<Integer> getFixCostIncidenceById(@PathVariable Long id){
        return new ResponseEntity<Integer>(iFinAnaServ.getFixCostIncidenceById(id), HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FinancialAnalysis>> getAllFinancialAnalysis(){
        return new ResponseEntity<>(iFinAnaServ.getAllFinancialAnalysis(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteFinancialAnalysis(@PathVariable Long id){
        return new ResponseEntity<>(iFinAnaServ.deleteFinancialAnalysis(id), HttpStatus.OK);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<FinancialAnalysis> editFinancialAnalysis(@RequestBody FinancialDto financialDto,
                                                                   @PathVariable Long id){
        return new ResponseEntity<>(iFinAnaServ.editFinancialAnalysis(financialDto, id), HttpStatus.OK);
    }

}
