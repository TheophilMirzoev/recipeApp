package me.theo.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.theo.recipeapp.models.Ingredient;
import me.theo.recipeapp.services.FilesService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class IngredientServiceImpl {

    private static int id;
    final private FilesService filesService;

    private Map<Integer, Ingredient> ingredientMap = new HashMap<>();

    @PostConstruct()
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.getMessage();
        }

    }

    public IngredientServiceImpl(FilesService filesService) {
        this.filesService = filesService;
    }

    public Ingredient addIngredient(Ingredient ingredient) {
      ingredientMap.put(id++, ingredient);
      saveToFile();
      return ingredient;
    }

    public Ingredient getIngredient(Integer id) {
     return ingredientMap.get(id);
    }

    public List<Ingredient> getAllIngredient() {
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.addAll(ingredientMap.values());
        return ingredientList;
    }

    public Ingredient editIngredient(Integer id, Ingredient ingredient) {
        Ingredient ingredient1 = ingredientMap.get(id);
        ingredient1.setIngredientName(ingredient.getIngredientName());
        saveToFile();
        return ingredient1;
    }

    public Ingredient deleteIngredient(Integer id) {
        Ingredient ingredient = ingredientMap.remove(id);
        return ingredient;
    }

    private void saveToFile() {
        try {
           String json = new ObjectMapper().writeValueAsString(ingredientMap);
           filesService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile() {
       String json = filesService.readFromFile();
        if (!(json.isBlank() && json.isEmpty())) {

            try {
                ingredientMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Ingredient>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
