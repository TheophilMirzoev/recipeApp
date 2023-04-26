package me.theo.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "ингредиенты", description = "операции по добавлению, получению, удалению и просмотру ингредиентов")
public class IngredientController {
    private IngredientServiceImpl ingredientService;

    public IngredientController(IngredientServiceImpl ingredientService) {
        this.ingredientService = ingredientService;
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "ингредиент создан"
            )})
    @PostMapping()
    @Operation(summary = "создание ингредиента",description = "создание ингредиента через тело запроса с помощью json")
    public ResponseEntity<Ingredient> addIngredient(@RequestBody Ingredient ingredient) {
       Ingredient ingredient1 = ingredientService.addIngredient(ingredient);
        if (ingredient1 == null) {
            return ResponseEntity.notFound().build();
        }
       return ResponseEntity.ok(ingredient1);
    }

    @GetMapping("/{id}")
    @Operation(summary = "получение ингредиента",description = "получение ингредиента по id")
    public ResponseEntity<Ingredient> getIngredient(@PathVariable Integer id) {
        Ingredient ingredient = ingredientService.getIngredient(id);
        if (ingredient == null) {
            ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredient);
    }
    @GetMapping("/all")
    @Operation(summary = "получить все ингредиенты", description = "все ингредиенты")
    public ResponseEntity<List<Ingredient>> getAllIngredient() {
        List<Ingredient> ingredientList = ingredientService.getAllIngredient();
        if (ingredientList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ingredientList);
    }

    @PutMapping("/{id}")
    @Operation(summary = "редактирование ингредиента",description = "редактирование ингредиента по id, через тело запроса")
    public ResponseEntity<Ingredient> editIngredient(@PathVariable Integer id, @RequestBody Ingredient name) {
        Ingredient ingredient = ingredientService.editIngredient(id, name);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(ingredient);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "удаление ингредиента",description = "удаление ингредиента по id")
    public ResponseEntity<Ingredient> deleteIngredient(@PathVariable Integer id) {
        Ingredient ingredient = ingredientService.deleteIngredient(id);
        if (ingredient == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
