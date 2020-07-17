package com.example.foodrecipes.requests

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.foodrecipes.AppExecutors
import com.example.foodrecipes.models.Recipe
import com.example.foodrecipes.responses.RecipeSearchResponse
import com.example.foodrecipes.util.Constants
import com.example.foodrecipes.util.Constants.NETWORK_TIMEOUT
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception
import java.util.concurrent.TimeUnit

object RecipeApiClient {

    private var mRecipes: MutableLiveData<List<Recipe>> = MutableLiveData()
    private const val TAG = "RecipeApiClient"
    private var mRetrieveRecipesRunnable: RetrieveRecipesRunnable? = null

    fun getRecipes(): LiveData<List<Recipe>>{
        return mRecipes
    }

    fun searchRecipesApi(query: String?,pageNumber: Int?){
        if(mRetrieveRecipesRunnable != null)
            mRetrieveRecipesRunnable = null
        mRetrieveRecipesRunnable =  RetrieveRecipesRunnable(query,pageNumber)
        val handler = AppExecutors.networkIO().submit(mRetrieveRecipesRunnable!!)

        AppExecutors.networkIO().schedule({
            // Let the user know its timed out
            handler.cancel(true)
        },NETWORK_TIMEOUT,TimeUnit.MILLISECONDS)
    }

    private class RetrieveRecipesRunnable(
        private var query: String? = null,
        private var pageNumber: Int? = 0,
        var cancelRequest: Boolean? = false): Runnable{

        constructor(query: String, pageNumber: Int): this(query,pageNumber,cancelRequest = false)

        override fun run() {
            try {
                val response: Response<RecipeSearchResponse> = getRecipes(query!!,pageNumber!!).execute()
                if (cancelRequest!!)
                    return
                if(response.code() == 200){
                    val list: ArrayList<Recipe> = response.body()!!.recipes as ArrayList
                    if (pageNumber == 1)
                        mRecipes.postValue(list)
                    else{
                        val currentRecipes: ArrayList<Recipe> = mRecipes.value!! as ArrayList<Recipe>
                        currentRecipes.addAll(list)
                        mRecipes.postValue(currentRecipes)
                    }
                }else{
                    val error: String = response.errorBody().toString()
                    Log.d(TAG, "error: $error")
                    mRecipes.postValue(null)
                }
            }catch (e: Exception){
                e.printStackTrace()
                mRecipes.postValue(null)
            }
            if (cancelRequest!!){
                return
            }
        }

        private fun getRecipes(query: String, pageNumber: Int): Call<RecipeSearchResponse>{
            return ServiceGenerator.getRecipeApi().searchRecipe(
                Constants.API_KEY,
                query,
                pageNumber.toString()
            )
        }

        internal fun cancelRequest(){
            Log.d(TAG, "cancelRequest: canceling the search request")
            cancelRequest = true
        }
    }

    fun cancelRequest(){
        mRetrieveRecipesRunnable?.cancelRequest()
    }

}