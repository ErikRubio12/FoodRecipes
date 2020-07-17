package com.example.foodrecipes.responses

import com.example.foodrecipes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeResponse(
            @SerializedName("recipe")
            @Expose
            val recipe: Recipe? = Recipe()
){

    constructor(): this(
        recipe = Recipe()
    )

    override fun toString(): String {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}'
    }
}