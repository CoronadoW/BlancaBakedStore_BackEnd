package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.ExpirationDto;
import com.coronado.blancabakedstore.dto.ResaleProdDto;
import com.coronado.blancabakedstore.dto.ResaleProdWithNearestDateDto;
import com.coronado.blancabakedstore.model.ResaleProduct;

import java.util.List;

public interface IResaleProductService {

    ResaleProdWithNearestDateDto createResaleProd(ResaleProdDto resaleProdDto);

    List<ResaleProdWithNearestDateDto> getAllWithNearestExpDate();

    ResaleProdWithNearestDateDto getResaleProdWNearestExpByCode(int productCode);

    ResaleProdWithNearestDateDto getResaleProdWNearestExpByName(String productName);

    ResaleProduct getResaleProduct(int productCode);

    ResaleProduct getResaleProductByProdName(String productName);

    List<ResaleProduct> getAllResaleProd();

    ResaleProduct deleteResaleProd(int productCode);

    ResaleProduct editResaleProd(ResaleProdDto resaleProdDto);

    ResaleProduct updatestock(int productCode, int newStock);

}
