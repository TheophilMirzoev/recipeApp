package me.theo.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.theo.recipeapp.models.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

@Service
public class RecipeServiceImpl {
    private static int id;
    final private FilesRecipeServiceImpl filesRecipeServiceImpl;
    private Map<Integer, Recipe> recipeMap = new HashMap<>();

    public RecipeServiceImpl(FilesRecipeServiceImpl filesRecipeServiceImpl) {
        this.filesRecipeServiceImpl = filesRecipeServiceImpl;
    }

    @PostConstruct()
    private void init() {
        readFromFile();
    }
    public Recipe addRecipe(Recipe recipe) {
      recipeMap.put(id++, recipe);
       saveToFile();
        return recipe;
    }

    public Recipe getRecipe(Integer integer) {
        return recipeMap.get(integer);
    }

    public List<Recipe> getAllRecipe() {
        List<Recipe> recipeList = new ArrayList<>();
        recipeList.addAll(recipeMap.values());
        return recipeList;
    }
    public Recipe editRecipe(Integer id, Recipe recipe) {
        Recipe recipe1 = recipeMap.get(id);
        if (!StringUtils.isBlank(recipe.getRecipeName())) {
            recipe1.setRecipeName(recipe.getRecipeName());
            saveToFile();
            return recipe1;
        }
        return null;
    }

    public Recipe deleteRecipe(Integer id) {
        Recipe recipe = recipeMap.remove(id);
        return recipe;
    }

//    public Path createRecipeReport(String suffix) {
//
//    }
    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesRecipeServiceImpl.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    private void readFromFile() {
        String json = filesRecipeServiceImpl.readFromFile();
            try {
                recipeMap = new ObjectMapper().readValue(json, new TypeReference<HashMap<Integer, Recipe>>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
    }
}
