package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.ExpirationDto;
import com.coronado.blancabakedstore.model.Expiration;

import java.util.List;

public interface IExpirationService {

        Expiration createExpiration(ExpirationDto expDto);

        Expiration createExpirationAssociatedToProduct(ExpirationDto expDto);

        Expiration getNearestExpirationByProductCode( int productCode);

        List<Expiration> getExpirationsByProductCode(int productCode);

        Expiration getNearestExpirationDate();

        List<Expiration> getExpirationsListByOrder();

        void updateExpirations(ExpirationDto expirationDto);

}
