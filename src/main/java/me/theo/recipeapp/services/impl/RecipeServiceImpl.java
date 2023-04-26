package me.theo.recipeapp.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.theo.recipeapp.models.Ingredient;
import me.theo.recipeapp.models.Recipe;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.util.*;

@Service
public class RecipeServiceImpl {
    private static int id;
    final private FilesRecipeServiceImpl filesRecipeServiceImpl;
    final private IngredientServiceImpl ingredientService;
    private final ObjectMapper  objectMapper;
    private Map<Integer, Recipe> recipeMap = new HashMap<>();

    public RecipeServiceImpl(FilesRecipeServiceImpl filesRecipeServiceImpl, IngredientServiceImpl ingredientService, ObjectMapper objectMapper) {
        this.filesRecipeServiceImpl = filesRecipeServiceImpl;
        this.ingredientService = ingredientService;
        this.objectMapper = objectMapper;
    }

    @PostConstruct()
    private void init() {
        try {
            readFromFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    private void saveToFile() {
        try {
            String json = new ObjectMapper().writeValueAsString(recipeMap);
            filesRecipeServiceImpl.saveToFile(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void addRecipeFromInputStream(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            List<Ingredient> ingredientList = new ArrayList<>();
            Map<Integer, String> steps = new HashMap<>();
            while ((line = reader.readLine()) != null) {
                String[] array = StringUtils.split(line, '|');
                ingredientList.add(new Ingredient(String.valueOf(array[0]), Integer.parseInt(array[1]),
                        String.valueOf(array[2])));
                steps.put(Integer.parseInt(array[3]), String.valueOf(array[4]));
                Recipe recipe = new Recipe(String.valueOf(array[5]), Integer.parseInt(array[6]),
                        ingredientList, steps);
                addRecipe(recipe);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
    public void exportFileFromMemory(PrintWriter writer) throws IOException {
        for (Recipe recipe : this.recipeMap.values()) {
            writer.println(recipe.getRecipeName());
            writer.println("время приготовления: %d минут".formatted(recipe.getCookingTime()));
            writer.println("ингридиенты:");
            for (Ingredient ingredient : recipe.getIngredientList()) {
                writer.println("\t%s - %d %s".formatted(ingredient.getIngredientName(), ingredient.getWeight(), ingredient.getIngredientName()));
            }
            writer.println("инструкция");
            for (int i = 0; i < recipe.getCookingSteps().size(); i++) {
                writer.println("%d. %s".formatted(i + 1, recipe.getCookingSteps().get(i)));
            }
        }
        writer.flush();
    }
}
