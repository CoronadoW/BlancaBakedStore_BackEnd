package com.coronado.blancabakedstore.service;

import com.coronado.blancabakedstore.dto.DetailProof;
import com.coronado.blancabakedstore.dto.RecipeDto;
import com.coronado.blancabakedstore.dto.SupplyPerRecipeDto;
import com.coronado.blancabakedstore.exceptions.EntityNotFoundException;
import com.coronado.blancabakedstore.model.RecipeDetail;
import com.coronado.blancabakedstore.model.Supply;
import com.coronado.blancabakedstore.repository.IRecipeDetailRepository;
import com.coronado.blancabakedstore.repository.ISupplyRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeDetailService implements IRecipeDetailService {

  private final IRecipeDetailRepository iReciDetRepo;
  private final ISupplyRepository iSupRepo;
    @Autowired
    public RecipeDetailService(IRecipeDetailRepository iReciDetRepo, ISupplyRepository iSupRepo) {
        this.iReciDetRepo = iReciDetRepo;
        this.iSupRepo = iSupRepo;
    }

    @Transactional
    public List<RecipeDetail> createRecipeDetailList(RecipeDto recipeDto){

        //Create an empty RecipeDetail List
        List<RecipeDetail> recipeDetailList = new ArrayList<>();

        //Get Supplies Per Recipe List
        List<SupplyPerRecipeDto> supDtoList = recipeDto.getSuppPerRecipeDtoList();

        //Create an instance for each SupplyPerRecipe
        for(SupplyPerRecipeDto supDto : supDtoList){
            RecipeDetail recipeDetail = new RecipeDetail();

            //Get Supplies by the name, if the Supply cannot be found throws an exception
            Supply supply = iSupRepo.findBySupplyNameIgnoreCase(supDto.getSuppNameDto())
                    .orElseThrow(() -> new EntityNotFoundException("Insumo no encontrado con el nombre: " + supDto.getSuppNameDto()));

            //Calculates cost per supply applying utilized quantity and waste percentage.
            Double cost = calculateCostPerSupplyPerRecipe(supDto, supply, recipeDto);

            //Get units quantity per recipe
            int unitsPerRecipe = recipeDto.getUnitsPerRecipeDto();

            //Set data to recipeDetail variable without RecipeCost assigned
            recipeDetail.setSuppName(supply.getSupplyName());
            recipeDetail.setSuppType(supply.getSupplyType());
            recipeDetail.setQtyUtilized(supDto.getUtilizedDto());
            recipeDetail.setWaste(supDto.getWasteDto());
            recipeDetail.setCostPerRecipe(cost);
            recipeDetail.setUnitCostPerSupp(cost / unitsPerRecipe);

            iReciDetRepo.save(recipeDetail);

            //Add recipeDetail to recipeDetailList
            recipeDetailList.add(recipeDetail);
        }
        return recipeDetailList;
    }

    //suppNameDto , utilized and waste
    public Double calculateCostPerSupplyPerRecipe(SupplyPerRecipeDto supDto, Supply supply, RecipeDto recipeDto){
        Double cost = supply.getCost();
        Double utilized = supDto.getUtilizedDto();
        Double waste = 1 - supDto.getWasteDto() / 100.0;
        /*
        int totalUnits = recipeDto.getUnitsPerRecipeDto();
        if(supply.getSupplyType().equalsIgnoreCase("Unidad")){
            (cost * utilized / waste) * totalUnits;
        }
        */
        return cost * utilized / waste;
    }

    public List<RecipeDetail> getAllRecipeDetail(){
        return iReciDetRepo.findAll();
    }

}
