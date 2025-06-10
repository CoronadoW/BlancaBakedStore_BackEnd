package com.coronado.blancabakedstore.controller;
import com.coronado.blancabakedstore.dto.RecipeCostRequestDto;
import com.coronado.blancabakedstore.dto.RecipeDto;
import com.coronado.blancabakedstore.model.RecipeCost;
import com.coronado.blancabakedstore.service.IRecipeCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recipeCost")
@CrossOrigin(origins = "http://localhost:4200")
public class RecipeCostController {
    private final IRecipeCostService iRecipeCostServ;
    @Autowired
    public RecipeCostController(IRecipeCostService iRecipeCostServ) {
        this.iRecipeCostServ = iRecipeCostServ;
    }


    @PostMapping("/create")
    public ResponseEntity<RecipeCost> createRecipeCost(@RequestBody RecipeDto recipeDto){
        return new ResponseEntity<>(iRecipeCostServ.createRecipeCost(recipeDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RecipeCost>> getAllRecipeCosts(){
        return new ResponseEntity<>(iRecipeCostServ.getAllRecipeCosts(), HttpStatus.OK);
    }

    @GetMapping("/get/{recipeName}")
    public ResponseEntity<RecipeCost> getRecipeByName(@PathVariable String recipeName) {
        RecipeCost recipe = iRecipeCostServ.getRecipeByName(recipeName.toUpperCase());
        return ResponseEntity.ok(recipe);
    }

    @DeleteMapping("/delete/{recipeName}")
    public ResponseEntity<Void> deleteRecipeCost(@PathVariable String recipeName){
        iRecipeCostServ.deleteRecipeCost(recipeName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
