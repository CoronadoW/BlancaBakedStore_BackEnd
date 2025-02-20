package com.coronado.blancabakedstore.controller;
import com.coronado.blancabakedstore.dto.DetailProof;
import com.coronado.blancabakedstore.dto.RecipeDto;
import com.coronado.blancabakedstore.model.RecipeDetail;
import com.coronado.blancabakedstore.service.IRecipeDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/recipeDetail")
public class RecipeDetailController {
    private final IRecipeDetailService iReciDetServ;
    @Autowired
    public RecipeDetailController(IRecipeDetailService iReciDetServ) {
        this.iReciDetServ = iReciDetServ;
    }

    @PostMapping("/createList")
    public ResponseEntity<List<RecipeDetail>> createRecipeDetailList(@RequestBody RecipeDto recipeDto){
        return new ResponseEntity<>(iReciDetServ.createRecipeDetailList(recipeDto), HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<RecipeDetail>> getAllRecipeDetail(){
        return new ResponseEntity<>(iReciDetServ.getAllRecipeDetail(), HttpStatus.OK);
    }

}
