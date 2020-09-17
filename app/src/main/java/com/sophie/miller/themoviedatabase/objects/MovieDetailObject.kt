package com.sophie.miller.themoviedatabase.objects

class MovieDetailObject(val title: String, val original_language: String,
                        val genres: List<GenresObject>?, val overview: String,
                        val release_date: String, val poster_path: String) {
}

class GenresObject(val id : Int, val name: String)