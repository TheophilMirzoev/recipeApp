package me.theo.recipeapp.controllers;

import me.theo.recipeapp.models.Ingredient;
import me.theo.recipeapp.models.Recipe;
import me.theo.recipeapp.services.impl.IngredientServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ingredient")
public class IngredientController {
    private IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @PostMapping()
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
       Ingredient ingredient1 = ingredientService.addIngredient(ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.ok(ingredient1);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Integer id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable Integer id, @RequestBody Ingredient name) {
        Ingredient ingredient = ingredientService.editIngredient(id, name);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Integer id) {
        Ingredient ingredient = ingredientService.deleteIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
