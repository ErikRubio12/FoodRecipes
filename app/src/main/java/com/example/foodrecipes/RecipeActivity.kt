package com.example.foodrecipes

import android.os.Bundle
import android.util.Log
import com.example.foodrecipes.models.Recipe

class RecipeActivity: BaseActivity() {

    companion object{
        const val TAG = "RecipeActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe)

        getIncomingIntent()
    }

    private fun getIncomingIntent(){
        if(intent.hasExtra("recipe")){
            val recipe = intent.getParcelableExtra<Recipe>("recipe")
            Log.d(TAG, "getIncomingintent: " + recipe?.title)
        }
    }
}