package me.theo.recipeapp.services.impl;

import me.theo.recipeapp.models.Ingredient;
import me.theo.recipeapp.models.Recipe;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Service
public class IngredientServiceImpl {
    private static int id;

    private Map<Integer, Ingredient> ingredientMap = new HashMap<>();

    public Ingredient addIngredient(Ingredient ingredient) {
      ingredientMap.put(id++, ingredient);
        return ingredient;
    }

    public Ingredient getIngredient(Integer id) {
     return ingredientMap.get(id);
    }

    public Ingredient editIngredient(Integer id, Ingredient ingredient) {
        Ingredient ingredient1 = ingredientMap.get(id);
        ingredient1.setIngredientName(ingredient.getIngredientName());
        return ingredient1;
    }

    public Ingredient deleteIngredient(Integer id) {
        Ingredient ingredient = ingredientMap.remove(id);
        return ingredient;
    }
}
