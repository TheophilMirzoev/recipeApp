package me.theo.recipeapp.services.impl;

import me.theo.recipeapp.models.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class RecipeServiceImpl {

    private static int id;
    private Map<Integer, Recipe> recipeMap = new HashMap<>();
    public Recipe addRecipe(Recipe recipe) {
      recipeMap.put(id++, recipe);
        return recipe;
    }

    public Recipe getRecipe(Integer integer) {
        return recipeMap.get(integer);
    }
    public Recipe editRecipe(Integer id, Recipe recipe) {
        Recipe recipe1 = recipeMap.get(id);
        if (StringUtils.isBlank(recipe.getRecipeName())) {
            recipe1.setRecipeName(recipe.getRecipeName());
            return recipe1;
        }
        return null;
    }

    public Recipe deleteRecipe(Integer id) {
        Recipe recipe = recipeMap.remove(id);
        return recipe;
    }



}
