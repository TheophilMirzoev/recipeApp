package me.theo.recipeapp.controllers;

import me.theo.recipeapp.models.Ingredient;
import me.theo.recipeapp.models.Recipe;
import me.theo.recipeapp.services.impl.IngredientServiceImpl;
import me.theo.recipeapp.services.impl.RecipeServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recipe")
//RequestMapping для того, что бы все методы контроллера работали с одним URL(например только /info, который указывать в этой
// аннотации, то что указываешь в методах будет суммироваться(info/info))
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping()
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.addRecipe(recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Integer id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> editRecipe(@PathVariable Integer id, @RequestBody Recipe name) {
        Recipe recipe1 = recipeService.editRecipe(id, name);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(recipe1);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Integer id) {
       Recipe recipe =recipeService.deleteRecipe(id);
        if (recipe == null) {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}

