package me.theo.recipeapp.controllers;

import me.theo.recipeapp.models.Ingredient;
import me.theo.recipeapp.services.impl.IngredientServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
public class IngredientController {
    private IngredientServiceImpl ingredientService;
    @PostMapping("/ingredient/add")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        return ingredientService.addIngredient(ingredient);
    }

    @GetMapping("/ingredient/get")
    public Ingredient getIngredient(@RequestParam Integer integer) {
        return  ingredientService.getIngredient(integer);
    }
}
