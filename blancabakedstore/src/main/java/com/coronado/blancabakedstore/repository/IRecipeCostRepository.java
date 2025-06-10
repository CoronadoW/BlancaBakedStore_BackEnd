package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.RecipeCost;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRecipeCostRepository extends JpaRepository<RecipeCost, Long> {

    Optional<RecipeCost> findByRecipeName(String recipeName);

    void deleteByRecipeName(String recipeName);

    boolean existsByRecipeName(String recipeName);

}


