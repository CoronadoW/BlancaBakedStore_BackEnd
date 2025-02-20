package com.coronado.blancabakedstore.service;
import com.coronado.blancabakedstore.model.Supply;

import java.util.List;

public interface ISupplyService {

    Supply createSupply(Supply supply);

    Supply getSupply(int supplyCode);

    List<Supply> getAllSupply();

    String deleteSupply(int supplyCode);

    Supply editSupply(Supply supply);

    List<Supply> getSupplyListBySupplyType(String supplyType);

    List<Supply> getSupplyListBySupplyName(String supplyName);

    Supply getSupplyBySupplyName(String supplyName);

}
