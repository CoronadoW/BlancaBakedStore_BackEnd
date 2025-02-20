package com.coronado.blancabakedstore.controller;
import com.coronado.blancabakedstore.dto.RecipeCostRequestDto;
import com.coronado.blancabakedstore.dto.RecipeDto;
import com.coronado.blancabakedstore.service.IRecipeCostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipeCost")
public class RecipeCostController {
    private final IRecipeCostService iRecipeCostServ;
    @Autowired
    public RecipeCostController(IRecipeCostService iRecipeCostServ) {
        this.iRecipeCostServ = iRecipeCostServ;
    }


    @PostMapping("/create/{id}")
    public ResponseEntity<String> createRecipeCost(@RequestBody RecipeDto recipeDto,
                                                   @PathVariable Long id){
        return new ResponseEntity<>("Costo Receta creado exitosamente :" + iRecipeCostServ.createRecipeCost(recipeDto, id), HttpStatus.CREATED);
    }
    /*
    @PostMapping("/create")
    public ResponseEntity<String> createRecipeCost(@RequestBody RecipeCostRequestDto recipeCostRequestDto){
        return new ResponseEntity<>("Costos por Receta creado satisfactoriamente: " + iRecipeCostServ.createRecipeCost(recipeCostRequestDto), HttpStatus.CREATED);
    }
    */

}
