package me.theo.recipeapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class Recipe {
    private String recipeName;
    private int cookingTime;
    private List<Ingredient> ingredientList;
    private Map<Integer, String> cookingSteps = new HashMap<>();
}
