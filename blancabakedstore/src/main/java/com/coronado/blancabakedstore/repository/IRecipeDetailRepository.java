package com.coronado.blancabakedstore.repository;

import com.coronado.blancabakedstore.model.RecipeDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRecipeDetailRepository extends JpaRepository<RecipeDetail, Long> {
}
