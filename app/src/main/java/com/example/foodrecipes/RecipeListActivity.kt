package com.example.foodrecipes

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.adapters.OnRecipeListener
import com.example.foodrecipes.adapters.RecipeRecyclerAdapter
import com.example.foodrecipes.models.Recipe
import com.example.foodrecipes.util.Testing
import com.example.foodrecipes.util.VerticalSpacingItemDecorator
import com.example.foodrecipes.viewmodels.RecipeListViewModel
import kotlinx.android.synthetic.main.activity_recipe_list.*

class RecipeListActivity : BaseActivity(), OnRecipeListener {

    private var mRecipeListViewModel: RecipeListViewModel? = null
    private var recipeRecyclerAdapter: RecipeRecyclerAdapter? = null

    companion object{
        const val TAG: String = "RecipeListActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipe_list)
        setSupportActionBar(toolbar)
        mRecipeListViewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)

        initRecyclerView()
        subscribeObservers()
        initSearchView()

        if(!mRecipeListViewModel!!.mIsViewinRecipes)
            displaySearchCategories()
    }

    private fun subscribeObservers(){
        mRecipeListViewModel!!.getRecipes().observe(this,
            Observer<List<Recipe>> {list ->
                if (!list.isNullOrEmpty()) {
                    if (mRecipeListViewModel?.mIsViewinRecipes!!) {
                        Testing.printRecipes(list, "Recipes Test")
                        mRecipeListViewModel?.mIsPerformingQuery = false
                        recipeRecyclerAdapter?.setRecipes(list)
                    }
                }
            })
    }

    private fun initRecyclerView(){
        recipeRecyclerAdapter = RecipeRecyclerAdapter(this)
        val itemDecorator = VerticalSpacingItemDecorator(30)
        recipe_list.addItemDecoration(itemDecorator)
        recipe_list.adapter = recipeRecyclerAdapter
        recipe_list.layoutManager = LinearLayoutManager(this)
        recipe_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if(!recipe_list.canScrollVertically(1)){
                    mRecipeListViewModel?.searchNextPage()
                }
            }
        })
    }

    private fun initSearchView(){
        search_view.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                recipeRecyclerAdapter!!.displayLoading()
                mRecipeListViewModel!!.searchRecipeApi(query!!,1)
                search_view.clearFocus()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }

    override fun onRecipeClick(position: Int) {
        val intent = Intent(this, RecipeActivity::class.java)
        intent.putExtra("recipe",recipeRecyclerAdapter?.getSelectedRecipe(position))
        startActivity(intent)
    }

    override fun onCategoryClick(category: String) {
        recipeRecyclerAdapter?.displayLoading()
        mRecipeListViewModel!!.searchRecipeApi(category,1)
        search_view.clearFocus()
    }

    private fun displaySearchCategories(){
        mRecipeListViewModel?.mIsViewinRecipes = false
        recipeRecyclerAdapter?.displaySearchCategories()
    }

    override fun onBackPressed() {
        if(mRecipeListViewModel?.onBackPressed()!!){
            super.onBackPressed()
        }else{
            displaySearchCategories()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.recipe_search_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_categories ->{
                displaySearchCategories()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
