package com.example.foodrecipes.requests

import com.example.foodrecipes.util.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceGenerator {

    private val retrofitBuilder = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

    private val retrofit = retrofitBuilder.build()

    private val recipeApi = retrofit.create(RecipeApi::class.java)

    fun getRecipeApi(): RecipeApi{
        return recipeApi
    }

}