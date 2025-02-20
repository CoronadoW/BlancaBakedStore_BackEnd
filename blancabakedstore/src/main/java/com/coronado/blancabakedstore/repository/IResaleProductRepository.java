package com.coronado.blancabakedstore.repository;
import com.coronado.blancabakedstore.model.ResaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IResaleProductRepository extends JpaRepository<ResaleProduct, Long> {

    Optional<ResaleProduct> findByProductCode(int productCode);

    boolean existsByProductCode(int productCode);

    void deleteByProductCode(int productCode);

}
