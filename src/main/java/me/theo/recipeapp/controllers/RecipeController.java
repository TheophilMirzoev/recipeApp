package me.theo.recipeapp.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import me.theo.recipeapp.models.Ingredient;
import me.theo.recipeapp.models.Recipe;
import me.theo.recipeapp.services.impl.IngredientServiceImpl;
import me.theo.recipeapp.services.impl.RecipeServiceImpl;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recipe")
@Tag(name = "рецепты", description = "операции по добавлению, получению, удалению и просмотру рецептов")
//RequestMapping для того, что бы все методы контроллера работали с одним URL(например только /info, который указывать в этой
// аннотации, то что указываешь в методах будет суммироваться(info/info))
public class RecipeController {
    private final RecipeServiceImpl recipeService;

    public RecipeController(RecipeServiceImpl recipeService) {
        this.recipeService = recipeService;
    }


    @PostMapping()
    @Operation(summary = "создание рецепта",description = "создание рецепта через тело запроса в json")
    public ResponseEntity<Recipe> addRecipe(@RequestBody Recipe recipe) {
        Recipe recipe1 = recipeService.addRecipe(recipe);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipe1);
    }
    @PostMapping(value = "/import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "импорт рецепта",description = "импорт рецепта из файла")
    public ResponseEntity<Object> addRecipeFromFile(@RequestParam MultipartFile multipartFile) {
        try (InputStream stream = multipartFile.getInputStream()) {
            recipeService.addRecipeFromInputStream(stream);
            return ResponseEntity.ok().build();
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.toString());
        }
    }


    @GetMapping("/{id}")
    @Operation(summary = "получить рецепт",description = "получение рецепта по id")
    public ResponseEntity<Recipe> getRecipe(@PathVariable Integer id) {
        Recipe recipe = recipeService.getRecipe(id);
        if (recipe == null) {
            return ResponseEntity.status(400).build();
        }
        return ResponseEntity.ok(recipe);
    }

    @GetMapping("/all")
    @Operation(summary = "получить все рецепты", description = "все рецепты")
    public ResponseEntity<List<Recipe>> getAllRecipe() {
        List<Recipe> recipeList = recipeService.getAllRecipe();
        if (recipeList == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(recipeList);
    }

    @PutMapping("/{id}")
    @Operation(summary = "изменить рецепт",description = "редактирование рецепта по id, через тело запроса")
    public ResponseEntity<Recipe> editRecipe(@PathVariable Integer id, @RequestBody Recipe name) {
        Recipe recipe1 = recipeService.editRecipe(id, name);
        if (recipe1 == null) {
            return ResponseEntity.notFound().build();
        }
        return  ResponseEntity.ok(recipe1);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "удаление рецепта",description = "удаление рецепта по id")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Integer id) {
       Recipe recipe =recipeService.deleteRecipe(id);
        if (recipe == null) {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}

