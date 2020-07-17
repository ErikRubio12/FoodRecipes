package com.example.foodrecipes.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.foodrecipes.models.Recipe
import com.example.foodrecipes.repositories.RecipeRepository

class RecipeListViewModel: ViewModel() {

    internal var  mIsViewinRecipes: Boolean = false
    internal var mIsPerformingQuery: Boolean = false

    fun getRecipes(): LiveData<List<Recipe>>{
        return RecipeRepository.getRecipes()
    }

    fun searchRecipeApi(query: String, pageNumber: Int){
        mIsViewinRecipes = true
        mIsPerformingQuery = true
        RecipeRepository.searchRecipeApi(query,pageNumber)
    }

    fun searchNextPage(){
        if(!mIsPerformingQuery && mIsViewinRecipes){
            RecipeRepository.searchNextPage()
        }
    }

    fun onBackPressed(): Boolean{
        if (mIsPerformingQuery){
            RecipeRepository.cancelRequest()
            mIsPerformingQuery = false
        }
        if(mIsViewinRecipes){
            mIsViewinRecipes = false
            return false
        }
        return true
    }

}