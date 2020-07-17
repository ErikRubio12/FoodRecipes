package com.example.foodrecipes.adapters

import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R

class RecipeViewHolder(itemView: View, onRecipeListener: OnRecipeListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var title: TextView? = null
    var publisher: TextView? = null
    var socialScore: TextView? = null
    var image: AppCompatImageView? = null
    private var mOnRecipeListener: OnRecipeListener? = null

    init {
        title = itemView.findViewById(R.id.recipe_title)
        publisher = itemView.findViewById(R.id.recipe_publisher)
        socialScore = itemView.findViewById(R.id.recipe_social_score)
        image = itemView.findViewById(R.id.recipe_image)
        mOnRecipeListener = onRecipeListener

        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        mOnRecipeListener?.onRecipeClick(adapterPosition)
    }
}