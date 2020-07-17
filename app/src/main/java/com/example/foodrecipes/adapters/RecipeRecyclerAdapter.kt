package com.example.foodrecipes.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodrecipes.R
import com.example.foodrecipes.models.Recipe
import com.example.foodrecipes.util.Constants
import kotlin.math.roundToLong

class RecipeRecyclerAdapter(
   /* private var context: Context,*/
    private var onRecipeListener: OnRecipeListener? = null
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mRecipes: List<Recipe>? = emptyList()

    companion object{
        const val RECIPE_TYPE = 1
        const val LOADING_TYPE = 2
        const val CATEGORY_TYPE = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val v: View
        return when(viewType){
            RECIPE_TYPE ->{
                v = LayoutInflater.from(parent.context).inflate(R.layout.layout_recipe_list_item,parent,false)
                RecipeViewHolder(v,onRecipeListener!!)
            }
            LOADING_TYPE ->{
                v = LayoutInflater.from(parent.context).inflate(R.layout.layout_loading_list_item,parent,false)
                LoadingViewHolder(v)
            }
            CATEGORY_TYPE ->{
                v = LayoutInflater.from(parent.context).inflate(R.layout.layout_category_list_item,parent,false)
                CategoryViewHolder(v,onRecipeListener!!)
            }
            else ->{
                v = LayoutInflater.from(parent.context).inflate(R.layout.layout_recipe_list_item,parent,false)
                RecipeViewHolder(v,onRecipeListener!!)
            }
        }
    }

    override fun getItemCount(): Int = mRecipes?.size!!

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            RECIPE_TYPE ->{
                val holderRecipe = holder as RecipeViewHolder
                holderRecipe.title?.apply {
                    text = mRecipes?.get(position)?.title
                }
                holderRecipe.publisher?.apply {
                    text = mRecipes?.get(position)?.publisher
                }
                holderRecipe.socialScore?.apply {
                    text = mRecipes?.get(position)?.social_rank?.roundToLong().toString()
                }

                holderRecipe.image?.let {
                    Glide.with(holderRecipe.itemView.context)
                        .setDefaultRequestOptions(RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background))
                        .load(mRecipes?.get(position)?.image_url)
                        .into(it)
                }
            }
            LOADING_TYPE ->{
                /*val holderLoading = holder as LoadingViewHolder
                val dotProgressBar = DotProgressBar.Builder()
                    .setMargin(4)
                    .setAnimationDuration(200)
                    .setDotBackground(R.drawable.ic_launcher_background)
                    .setMaxScale(1f)
                    .setMinScale(0.3f)
                    .setNumberOfDots(5)
                    .setdotRadius(8)
                    .build(context)
                holderLoading.dotProgress!!.addView(dotProgressBar)
                dotProgressBar.startAnimation()*/
            }
            CATEGORY_TYPE ->{
                val holderCategory = holder as CategoryViewHolder
                holder.category_title?.apply {
                    text = mRecipes?.get(position)?.title
                }
                val path = Uri.parse("android.resource://com.example.foodrecipes/drawable/" + mRecipes?.get(position)?.image_url)
                holderCategory.category_image?.let {
                    Glide.with(holderCategory.itemView.context)
                        .setDefaultRequestOptions(RequestOptions()
                            .placeholder(R.drawable.ic_launcher_background))
                        .load(path)
                        .into(it)
                }
            }
        }
    }

    fun setRecipes(recipes: List<Recipe>){
        mRecipes = recipes
        notifyDataSetChanged()
    }

    fun displayLoading(){
        if(!isLoading()){
            val recipe = Recipe()
            recipe.title = "LOADING..."
            val loadingList = ArrayList<Recipe>()
            loadingList.add(recipe)
            mRecipes = loadingList
            notifyDataSetChanged()
        }
    }

    private fun isLoading(): Boolean{
        return if(mRecipes?.size!! > 0)
                mRecipes?.get(mRecipes?.size!! - 1)?.title == "LOADING..."
            else
                false
    }

    fun displaySearchCategories(){
        val categories = arrayListOf<Recipe>()
        for(i in Constants.DEFAULT_SEARCH_CATEGORIES.indices){
            val recipe = Recipe()
            recipe.title = Constants.DEFAULT_SEARCH_CATEGORIES[i]
            recipe.image_url = Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]
            recipe.social_rank = (-1).toDouble()
            categories.add(recipe)
        }
        mRecipes = categories
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            mRecipes?.get(position)?.title == "LOADING..." -> LOADING_TYPE
            mRecipes?.get(position)?.social_rank?.toInt() == - 1 -> CATEGORY_TYPE
            position == (mRecipes!!.size - 1)
                    && position != 0
                    && mRecipes?.get(position)?.title != "EXHAUSTED..." -> LOADING_TYPE
            else -> RECIPE_TYPE
        }
    }

    fun getSelectedRecipe(position: Int):Recipe?{
        mRecipes?.let {
            if(it.isNotEmpty()){
               return mRecipes?.get(position)!!
            }
        }
        return null
    }
}