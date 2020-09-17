package com.sophie.miller.themoviedatabase.objects

import com.google.gson.JsonObject
import com.google.gson.annotations.SerializedName

class MovieIdItem(@SerializedName("results")
                  val moviesList : List<MovData>) {

}

class MovData(@SerializedName("id") val id: Int, @SerializedName("adult") val adult: Boolean?)