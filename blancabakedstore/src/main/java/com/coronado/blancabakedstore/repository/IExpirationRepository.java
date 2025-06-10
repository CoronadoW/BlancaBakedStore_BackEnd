package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.Expiration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IExpirationRepository extends JpaRepository<Expiration, Long> {

    //filter by productCode one ResaleProduct, and order its Expirations by expireDate ascendant, oldest first. The method get the oldest Expiration.
    Optional<Expiration> findFirstByResaleProductProductCodeOrderByExpireDateAsc(int productCode);

    //Get all Expirations of a ResaleProduct, but get it ordered by expireDate, Oldest first.
    List<Expiration> findAllByResaleProductProductCodeOrderByExpireDateAsc(int productCode);

    //Optional<Expiration> findByResaleProductProductCodeAndExpireDate(int productCode,LocalDate expireDate);

    //Method to find  Expiration by its associated ResaleProduct by its productCode.
    List<Expiration> findByResaleProductProductCode(int productCode);

    //Get the Expiration with nearest expiration date to "date" of parameter
    Optional<Expiration> findFirstByExpireDateGreaterThanEqualOrderByExpireDateAsc(LocalDate date);

    //Find all Expiration with has expiration date greater than "date", order these in a list of Expiration
    List<Expiration> findAllByExpireDateGreaterThanEqualOrderByExpireDateAsc(LocalDate date);

}
