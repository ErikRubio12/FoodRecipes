package com.example.foodrecipes.repositories

import androidx.lifecycle.LiveData
import com.example.foodrecipes.models.Recipe
import com.example.foodrecipes.requests.RecipeApiClient

object RecipeRepository {

    private var mQuery: String? = null
    private var mPageNumber: Int? = null

    fun getRecipes(): LiveData<List<Recipe>> {
        return RecipeApiClient.getRecipes()
    }

    fun searchRecipeApi(query: String, pageNumber: Int){
        var pageNumberToGo = pageNumber
        if(pageNumberToGo == 0)
            pageNumberToGo = 1
        mQuery = query
        mPageNumber = pageNumber
        RecipeApiClient.searchRecipesApi(query,pageNumberToGo)
    }

    fun searchNextPage(){
        searchRecipeApi(mQuery!!, mPageNumber!! + 1)
    }

    fun cancelRequest(){
        RecipeApiClient.cancelRequest()
    }

}