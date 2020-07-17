package com.example.foodrecipes.models

import android.os.Parcel
import android.os.Parcelable
import java.util.*

data class Recipe(
    var title: String? = "",
    var publisher: String? = "",
    var ingredients: List<String>?,
    var recipe_id: String? = "",
    var image_url: String? = "",
    var social_rank: Double? = 0.0): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.createStringArrayList(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double
    )

    constructor(): this(
        title = "",
        publisher = "",
        ingredients = emptyList(),
        recipe_id = "",
        image_url = "",
        social_rank = 0.0
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(publisher)
        parcel.writeStringList(ingredients)
        parcel.writeString(recipe_id)
        parcel.writeString(image_url)
        parcel.writeValue(social_rank)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Recipe> {
        override fun createFromParcel(parcel: Parcel): Recipe {
            return Recipe(parcel)
        }

        override fun newArray(size: Int): Array<Recipe?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + listOf(ingredients) +
                ", recipe_id='" + recipe_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", social_rank=" + social_rank +
                '}'
    }
}
