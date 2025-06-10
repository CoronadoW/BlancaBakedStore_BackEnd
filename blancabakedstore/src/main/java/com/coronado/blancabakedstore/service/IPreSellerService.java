package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.PreSellerDto;
import com.coronado.blancabakedstore.model.PreSeller;
import com.coronado.blancabakedstore.model.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPreSellerService {

    PreSeller createPreSeller(PreSellerDto preSellerDto);

    PreSeller getPreSeller(String preSellerName);

    List<PreSeller> getAllPreSellers();

    Page<PreSeller> getAllPreSellersPaginated(Pageable pageable);

    List<PreSeller> getPreSellerContainingName(String preSellerName);

    void deletePreSeller(String preSellerName);

    PreSeller editPreSeller(PreSellerDto preSellerDto);

    List<Purchase> getPurchaseListByPreSeller(String preSellerName);

    List<Purchase> getPurchasesByPreSellerOrderedByDate(String preSellerName);
}
