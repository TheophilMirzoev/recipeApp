package me.theo.recipeapp.controllers;

import me.theo.recipeapp.models.Ingredient;
import me.theo.recipeapp.models.Recipe;
import me.theo.recipeapp.services.impl.IngredientServiceImpl;
import me.theo.recipeapp.services.impl.RecipeServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
//RequestMapping для того, что бы все методы контроллера работали с одним URL(например только /info, который указывать в этой
// аннотации, то что указываешь в методах будет суммироваться(info/info))
public class RecipeController {
    RecipeServiceImpl recipeService;
    IngredientServiceImpl ingredientService;

    @GetMapping("/recipe/add")
    public Recipe addNewRecipe(@RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.addNewRecipe(recipe);
        return recipe1;
    }

    @GetMapping("/recipe/get")
    public Recipe getRecipe(@RequestBody Integer integer) {
        Recipe recipe1 = recipeService.gettingRecipe(integer);
        return recipe1;
    }

    @GetMapping("/ingredient/add")
    public Ingredient addIngredient(@RequestBody Ingredient ingredient) {
        Ingredient ingredient1 = ingredientService.addIngredient(ingredient);
        return ingredient1;
    }

    @GetMapping("/ingredient/get")
    public Ingredient getIngredient(@RequestBody Integer integer) {
        Ingredient ingredient1 = ingredientService.gettingIngredient(integer);
        return ingredient1;
    }

}

