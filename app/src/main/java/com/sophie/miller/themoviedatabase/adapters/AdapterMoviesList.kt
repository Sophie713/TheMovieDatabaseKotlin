package com.sophie.miller.themoviedatabase.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sophie.miller.themoviedatabase.MainActivity
import com.sophie.miller.themoviedatabase.R
import com.sophie.miller.themoviedatabase.constants.UIconstants.FRAGMENT_DETAIL
import com.sophie.miller.themoviedatabase.holders.HolderMoviePreview
import com.sophie.miller.themoviedatabase.objects.MovieDetailObject
import com.sophie.miller.themoviedatabase.utils.Logger
import com.sophie.miller.themoviedatabase.viewmodel.MainViewModel
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class AdapterMoviesList(val activity: MainActivity, val mainViewModel: MainViewModel) :
    RecyclerView.Adapter<HolderMoviePreview>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolderMoviePreview {
        val view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
        return HolderMoviePreview(view)
    }

    override fun getItemCount(): Int {
        return mainViewModel.moviesIDList.size
    }

    override fun onBindViewHolder(holder: HolderMoviePreview, position: Int) {
        holder.binding.root.setOnClickListener{
                _ -> mainViewModel.selectedMovieId = position
            activity.setFragment(FRAGMENT_DETAIL)


        }
        if (mainViewModel.moviesDetails.size > position && mainViewModel.moviesDetails[position].title != activity.getString(R.string.no_info)) {
            Logger.testLog("filled view posititon ${position} movies list size ${mainViewModel.moviesDetails.size}")
            Picasso.with(activity)
                .load("https://image.tmdb.org/t/p/w185/${mainViewModel.moviesDetails[position].poster_path}")
                .placeholder(R.drawable.unnamed)
                .into(holder.binding.itemMovieImage)
            holder.binding.itemMovieTitle.text = mainViewModel.moviesDetails[position].title
        } else {
            mainViewModel.moviesDetails.add(getEmptyMovieObject())
            loadMovieDetail(position)
        }
    }

    /**
     * load movie details
     * on success add to list on fail add empty item
     */
    private fun loadMovieDetail(position: Int) {
        val call: Call<MovieDetailObject> =
            mainViewModel.retrofitAPI.getMovieDetails(mainViewModel.moviesIDList[position])
        call.enqueue(object : Callback<MovieDetailObject> {
            override fun onFailure(call: Call<MovieDetailObject>, t: Throwable) {
                Logger.testLog("load detail failure ${position} movies list size ${mainViewModel.moviesDetails.size}")
            }

            override fun onResponse(
                call: Call<MovieDetailObject>,
                response: Response<MovieDetailObject>
            ) {
                //parse received data
                val movieDetails: MovieDetailObject? = response.body()
                if (movieDetails != null) {
                    try {
                        mainViewModel.moviesDetails.set(position, movieDetails)
                    } catch (e : Exception) {
                        Log.e("onResponse Exception", "${e.message}")
                    }
                    Logger.testLog("load details success ${position} movies list size ${mainViewModel.moviesDetails.size}")
                } else {
                    Logger.testLog("load details success parse ERROR ${position} movies list size ${mainViewModel.moviesDetails.size}")

                }
                notifyDataSetChanged()
            }
        })
    }

    /**
     * return empty movie object in case of error
     */
    private fun getEmptyMovieObject(): MovieDetailObject {
        return MovieDetailObject(activity.getString(R.string.no_info), "", null, "", "", "")
    }

    /**
     *  change movie selection
     */
    public fun setMovies(newMoviesList: ArrayList<String>) {
        mainViewModel.moviesIDList.clear()
        mainViewModel.moviesDetails.clear()
        mainViewModel.moviesIDList.addAll(newMoviesList)
        notifyDataSetChanged()
    }
}