package me.theo.recipeapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
@Data
@AllArgsConstructor
public class Recipe {
    //Поля класса рецепта должны содержать:
    //
    //    Название в формате строки;
    //    Время приготовления в минутах в формате целого положительного числа;
    //    Ингредиенты в формате списка объектов;
    //    Шаги приготовления в формате списка строк.
    //
    private String recipeName;
    private int cookingTime;
    private List<Ingredient> ingredientList;
    private List<String> cookingSteps;
}
