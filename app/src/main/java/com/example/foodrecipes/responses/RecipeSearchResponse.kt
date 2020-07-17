package com.example.foodrecipes.responses

import com.example.foodrecipes.models.Recipe
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RecipeSearchResponse(
    @SerializedName("count")
    @Expose
    val count: Int = 0,
    @SerializedName("recipes")
    @Expose
    val recipes: List<Recipe> = emptyList()
) {
    constructor(): this(
        count = 0,
        recipes = emptyList())

    override fun toString(): String {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + recipes +
                '}'
    }
}