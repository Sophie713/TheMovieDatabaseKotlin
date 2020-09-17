package com.sophie.miller.themoviedatabase.networking

import com.sophie.miller.themoviedatabase.constants.URLs.URL_BASE
import com.sophie.miller.themoviedatabase.constants.URLs.URL_LIST_NO_PARAM
import com.sophie.miller.themoviedatabase.constants.URLs.URL_MOVIE_PRE_ID
import com.sophie.miller.themoviedatabase.objects.MovieDetailObject
import com.sophie.miller.themoviedatabase.objects.MovieIdItem
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {

    companion object {
        fun create(): RetrofitAPI {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL_BASE)
                .build()
            return retrofit.create(RetrofitAPI::class.java)
        }
    }

    @GET(URL_LIST_NO_PARAM)
    fun getMoviesList(
        @Query("api_key") key: String,
        @Query("end_date") endDate: String,
        @Query("start_date") startDate: String
    ): Call<MovieIdItem>

    @GET(URL_MOVIE_PRE_ID)
    fun getMovieDetails(
        @Path("movie_id") id: String
    ): Call<MovieDetailObject>

}