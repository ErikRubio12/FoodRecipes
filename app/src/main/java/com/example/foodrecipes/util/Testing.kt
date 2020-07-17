package com.example.foodrecipes.util

import android.util.Log
import com.example.foodrecipes.models.Recipe

class Testing {

    companion object{
        fun printRecipes(list: List<Recipe>?, tag: String){
            list?.forEach { recipe ->
                Log.d(tag, "onChanged: " + recipe.title)
            }
        }
    }
}