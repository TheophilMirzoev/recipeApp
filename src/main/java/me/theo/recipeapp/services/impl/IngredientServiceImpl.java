package me.theo.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.theo.recipeapp.models.Ingredient;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class IngredientServiceImpl {

    private static int id;
    final private FilesIngredientServiceImpl filesIngredientService;

    private Map<Integer, Ingredient> ingredientMap = new HashMap<>();
    public IngredientServiceImpl(FilesIngredientServiceImpl filesIngredientService) {
        this.filesIngredientService = filesIngredientService;
    }

    @PostConstruct()
    private void init() {
       // try {
            readFromFile();
        //} catch (Exception e) {
       //     e.getMessage();
      //  }

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
            filesIngredientService.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile() {
       String json = filesIngredientService.readFromFile();
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
