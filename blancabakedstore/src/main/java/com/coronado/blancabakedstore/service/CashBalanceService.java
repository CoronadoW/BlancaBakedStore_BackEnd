package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.BalanceDto;
import com.coronado.blancabakedstore.dto.CashBalanceDto;
import com.coronado.blancabakedstore.exceptions.EntityAlreadyExistsException;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.CashBalance;
import com.coronado.blancabakedstore.repository.ICashBalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.util.List;

@Service
public class CashBalanceService implements ICashBalanceService {

    private final ICashBalanceRepository iCashBalRepo;

    @Autowired
    public CashBalanceService(ICashBalanceRepository iCashBalRepo) {
        this.iCashBalRepo = iCashBalRepo;
    }



    public CashBalance createCashBalance(CashBalanceDto cashBalanceDto) {
        if(iCashBalRepo.existsByDateAndShift(cashBalanceDto.getDate(), cashBalanceDto.getShift())){
            throw new EntityAlreadyExistsException("Cash balance already exists with that date and shift");
        }
        CashBalance cashBalance = new CashBalance();
        return iCashBalRepo.save(saveData(cashBalanceDto, cashBalance));
    }

    public CashBalance getCashBalance(Long id){
        return iCashBalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arqueo de caja no encontrado con Id: " + id));
    }

    public List<CashBalance> getAllCashBalance(){
        return iCashBalRepo.findAllByOrderByDateDesc();
    }

    //Method to get balance by date and shift
    public CashBalance getCashBalanceByDateAndShift(BalanceDto balanceDto){
        return iCashBalRepo.findByDateAndShift(balanceDto.getBalanceDateDto(), balanceDto.getBalanceShiftDto())
                .orElseThrow(()-> new EntityNotFoundException("Entity not found"));
    }

    public List<CashBalance> getCashBalanceByDate(LocalDate date){
        List<CashBalance> cashBalByDateList = iCashBalRepo.findByDateOrderByDateDesc(date);
        if(cashBalByDateList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró Arqueo de Caja con fecha: " + date);
        }
        return cashBalByDateList;
    }

    public List<CashBalance> getCashBalanceByShift(String shift){
        List<CashBalance> cashBalanceList = iCashBalRepo.findByShiftOrderByDateDesc(shift);
        if(cashBalanceList.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No se encontró Arqueos de caja con turno : " + shift);
        }
        return cashBalanceList;
    }

    public CashBalance deleteCashBalance(Long id){
        CashBalance cashBalance = iCashBalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arqueo de caja no encontrado con id: " + id));
            iCashBalRepo.deleteById(id);
        return cashBalance;
    }

    public CashBalance editCashBalance(CashBalanceDto cashBalanceDto, Long id){
        CashBalance cashBalance = iCashBalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Arqueo de caja no encontrado con id: " + id));
        return iCashBalRepo.save(saveData(cashBalanceDto, cashBalance));
    }

    public Double getTotalSales(CashBalanceDto cashBalanceDto) {
        Double cashSales = cashBalanceDto.getCashSales();
        Double cardSales = cashBalanceDto.getCardSales();
        Double transferSales = cashBalanceDto.getTransferSales();
        Double qrSales = cashBalanceDto.getQrSales();
        return cashSales + cardSales + transferSales + qrSales;
    }

    public Double getControl(CashBalanceDto cashBalanceDto){
        return getEndingCashBalance(cashBalanceDto) - cashBalanceDto.getSystemTotalSales();
       // Double totalSales =  getTotalSales(cashBalanceDto);
       // Double systemSales = cashBalanceDto.getSystemTotalSales();
       //  return totalSales - systemSales;
    }

    public Double getTotalPayments(CashBalanceDto cashBalanceDto){
        Double cashPay = cashBalanceDto.getCashPayments();
        Double otherPay = cashBalanceDto.getOtherPayments();
        return cashPay + otherPay;
    }

    public Double getEndingCashBalance(CashBalanceDto cashBalanceDto){
        return getTotalSales(cashBalanceDto) + getTotalPayments(cashBalanceDto);
    }

    //Set data into variable cashBalance
    public CashBalance saveData(CashBalanceDto cashBalanceDto, CashBalance cashBalance){
        cashBalance.setDate(cashBalanceDto.getDate());
        cashBalance.setShift(cashBalanceDto.getShift());
        cashBalance.setEmployeeName(cashBalanceDto.getEmployeeName());
        cashBalance.setOpeningCashBalance(cashBalanceDto.getOpeningCashBalance());
        cashBalance.setOpeningCashOnHand(cashBalanceDto.getOpeningCashOnHand());
        cashBalance.setCashSales(cashBalanceDto.getCashSales());
        cashBalance.setCardSales(cashBalanceDto.getCardSales());
        cashBalance.setTransferSales(cashBalanceDto.getTransferSales());
        cashBalance.setQrSales(cashBalanceDto.getQrSales());
        cashBalance.setSystemTotalSales(cashBalanceDto.getSystemTotalSales());
        cashBalance.setTotalSales(getTotalSales(cashBalanceDto));
        cashBalance.setControl(getControl(cashBalanceDto));
        cashBalance.setCashPayments(cashBalanceDto.getCashPayments());
        cashBalance.setOtherPayments(cashBalanceDto.getOtherPayments());
        cashBalance.setTotalPayments(getTotalPayments(cashBalanceDto));
        cashBalance.setEndingCashBalance(getEndingCashBalance(cashBalanceDto));
        cashBalance.setObservations(cashBalanceDto.getObservations());
        return cashBalance;
    }



}
