package com.sophie.miller.themoviedatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sophie.miller.themoviedatabase.constants.Api.MOVIE_DB_API_KEY
import com.sophie.miller.themoviedatabase.networking.RetrofitAPI
import com.sophie.miller.themoviedatabase.objects.MovieIdItem
import com.sophie.miller.themoviedatabase.objects.MovieDetailObject
import com.sophie.miller.themoviedatabase.utils.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(application: Application) : AndroidViewModel(application) {

    //list of filtered ids of films to show
    val moviesIDList = ArrayList<String>()
    //selected movie
    var selectedMovieId = 0
    //all filtered movies based on dates selected
     var moviesList: MutableLiveData<MovieIdItem>? = MutableLiveData()
    //all loaded movies details
    val moviesDetails = ArrayList<MovieDetailObject>()
    //retrofit
    val retrofitAPI = RetrofitAPI.create()
    var callMoviesList: Call<MovieIdItem> = retrofitAPI.getMoviesList(MOVIE_DB_API_KEY, "", "")

    init {
        initializeMovies()
        Logger.testLog("instantialized vm")
    }

    private fun initializeMovies(): LiveData<MovieIdItem> {
        val data: MutableLiveData<MovieIdItem> =
            MutableLiveData<MovieIdItem>()
        callMoviesList.enqueue(object : Callback<MovieIdItem> {

            override fun onResponse(
                call: Call<MovieIdItem>,
                response: Response<MovieIdItem>
            ) {
                moviesList?.setValue(response.body())
                Logger.testLog("success : ${response.body()} ${response.message()}")
            }

            override fun onFailure(call: Call<MovieIdItem>, t: Throwable) {
                Logger.testLog("failure ${t.message}")
            }
        })
        return data
    }

    fun editFilter(startDate: String, endDate: String){
        callMoviesList = retrofitAPI.getMoviesList(MOVIE_DB_API_KEY, endDate, startDate)
        initializeMovies()
    }
}