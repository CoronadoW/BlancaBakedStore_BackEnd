package com.coronado.blancabakedstore.repository;
import com.coronado.blancabakedstore.dto.ResaleProdWithNearestDateDto;
import com.coronado.blancabakedstore.model.ResaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IResaleProductRepository extends JpaRepository<ResaleProduct, Long> {

    Optional<ResaleProduct> findByProductCode(int productCode);

    Optional<ResaleProduct> findByProductName(String productName);

    boolean existsByProductCode(int productCode);

    boolean existsByProductName(String productName);

    void deleteByProductCode(int productCode);

    @Query("""
SELECT new com.coronado.blancabakedstore.dto.ResaleProdWithNearestDateDto(
    r.productCode,
    r.productName,
    r.unitType,
    r.stock,
    r.costPrice,
    r.packagingPrice,
    r.deliveryPrice,
    r.markingMargin,
    r.commission,
    r.totalCost,
    r.salePrice,
    r.marginalContribution,
    r.contributionMargin,
    r.salePriceWithCommission,
    r.marginalContribWithCommission,
    r.contribMarginWithCommission,
    e.expireDate,
    e.qty
    
)
FROM ResaleProduct r
JOIN r.expirations e
WHERE r.productCode = :productCode
    AND e.expireDate = (
    SELECT MIN(e2.expireDate)
    FROM Expiration e2
    WHERE e2.resaleProduct = r
        AND e2.expireDate >= CURRENT_DATE
)
""")
    Optional<ResaleProdWithNearestDateDto> findProductWithNearestExpireDateByCode(@Param("productCode") int productCode);

    @Query("""
SELECT new com.coronado.blancabakedstore.dto.ResaleProdWithNearestDateDto(
    r.productCode,
    r.productName,
    r.unitType,
    r.stock,
    r.costPrice,
    r.packagingPrice,
    r.deliveryPrice,
    r.markingMargin,
    r.commission,
    r.totalCost,
    r.salePrice,
    r.marginalContribution,
    r.contributionMargin,
    r.salePriceWithCommission,
    r.marginalContribWithCommission,
    r.contribMarginWithCommission,
    e.expireDate, 
    e.qty
    
)
FROM ResaleProduct r
JOIN r.expirations e
WHERE r.productName = :productName
    AND e.expireDate = (
    SELECT MIN(e2.expireDate)
    FROM Expiration e2
    WHERE e2.resaleProduct = r
        AND e2.expireDate >= CURRENT_DATE
)
""")
    Optional<ResaleProdWithNearestDateDto> findProductWithNearestExpireDateByName(@Param("productName") String productName);

}
