package me.theo.recipeapp.services.impl;

import me.theo.recipeapp.models.Ingredient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class IngredientServiceImpl {
    private static int id;

    private Map<Integer, Ingredient> ingredientMap;

    public Ingredient addIngredient(Ingredient ingredient) {
      ingredientMap.put(id++, ingredient);

        return ingredient;
    }

    public Ingredient getIngredient(Integer integer) {
     return ingredientMap.get(integer);
    }
}
