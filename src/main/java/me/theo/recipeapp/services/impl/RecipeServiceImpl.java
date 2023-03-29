package me.theo.recipeapp.services.impl;

import me.theo.recipeapp.models.Recipe;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class RecipeServiceImpl {

    private static int id;
    private Map<Integer, Recipe> recipeMap;
    public Recipe addRecipe(Recipe recipe) {
      recipeMap.put(id++, recipe);
        return recipe;
    }

    public Recipe getRecipe(Integer integer) {
      return recipeMap.get(integer);
    }

    // создать сервис, который будет хранить рецепты и возвращать рецепты по его идентификатору.
    // карте в формате <номер рецепта, рецепт>

}
