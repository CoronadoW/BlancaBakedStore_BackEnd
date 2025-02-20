package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.RecipeCost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecipeCostRepository extends JpaRepository<RecipeCost, Long> {
}
