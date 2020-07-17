package com.example.foodrecipes.adapters

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foodrecipes.R
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.layout_category_list_item.view.*

class CategoryViewHolder(itemView: View, onRecipeListener: OnRecipeListener): RecyclerView.ViewHolder(itemView), View.OnClickListener {

    var category_title: TextView? = null
    var category_image: CircleImageView? = null
    private var onRecipeListener: OnRecipeListener? = null

    init {
        this.onRecipeListener = onRecipeListener
        category_image = itemView.findViewById(R.id.category_image)
        category_title = itemView.findViewById(R.id.category_title)
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onRecipeListener?.onCategoryClick(category_title?.text.toString())
    }
}