package me.theo.recipeapp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredient {
    //Поля класса ингредиента должны содержать:
    //
    //    Название в формате строки;
    //    Количество ингредиентов в формате целого положительного числа;
    //    Единица измерения в формате строки.


    private String ingredientName;
    private int weight;
    private String unitOfMeasurement;
}
