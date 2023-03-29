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
    private RecipeServiceImpl recipeService;

    @PostMapping("/recipe/add")
    public Recipe addNewRecipe(@RequestBody Recipe recipe) {
        return recipeService.addRecipe(recipe);
    }

    @GetMapping("/recipe/get")
    public Recipe getRecipe(@RequestParam Integer integer) {
        return recipeService.getRecipe(integer);
    }



}

