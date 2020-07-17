package com.example.foodrecipes.requests

import com.example.foodrecipes.responses.RecipeResponse
import com.example.foodrecipes.responses.RecipeSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApi {

    // SEARCH
    @GET("api/search")
    fun searchRecipe(
        @Query("key") key: String,
        @Query("q") query: String,
        @Query("page") page: String): Call<RecipeSearchResponse>

    //GET RECIPE
    @GET("api/get")
    fun getRecipe(
        @Query("key") key:String,
        @Query("rId") rId:String): Call<RecipeResponse>

}