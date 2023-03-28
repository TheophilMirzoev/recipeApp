package me.theo.recipeapp.services.impl;

import me.theo.recipeapp.models.Recipe;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class RecipeServiceImpl implements me.theo.recipeapp.services.Service {

    private static int id;
    Map<Integer, Recipe> recipeMap;
    @Override
    public Recipe addNewRecipe(Recipe recipe) {
      recipeMap.put(id++, recipe);
        return recipe;
    }

    @Override
    public Recipe gettingRecipe(Integer integer) {
      return recipeMap.get(integer);
    }

    // создать сервис, который будет хранить рецепты и возвращать рецепты по его идентификатору.
    // карте в формате <номер рецепта, рецепт>

}
