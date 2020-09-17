package com.sophie.miller.themoviedatabase.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.sophie.miller.themoviedatabase.MainActivity

import com.sophie.miller.themoviedatabase.R
import com.sophie.miller.themoviedatabase.databinding.FragmentMovieDetailBinding
import com.sophie.miller.themoviedatabase.databinding.FragmentMoviesListBinding
import com.sophie.miller.themoviedatabase.viewmodel.MainViewModel
import com.squareup.picasso.Picasso

class FragmentMovieDetail : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activity: MainActivity
    private lateinit var binding: FragmentMovieDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie_detail, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            FragmentMovieDetail()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMovieDetailBinding.bind(view)
        //view model
        mainViewModel = ViewModelProvider(activity).get(MainViewModel::class.java)

        val selectedMovie = mainViewModel.moviesDetails[mainViewModel.selectedMovieId]
        //image
        Picasso.with(context)
            .load("https://image.tmdb.org/t/p/w185/${selectedMovie.poster_path}")
            .placeholder(R.drawable.unnamed)
            .into(binding.fragmentMovieDetailPoster)
        //title
        binding.fragmentMovieDetailTitle.text = selectedMovie.title
        //genres
        if (selectedMovie.genres != null) {
            var genres = ""
            for (i in 0 until selectedMovie.genres.size) {
                genres += selectedMovie.genres[i].name
                if (i < selectedMovie.genres.size - 1) {
                    genres += ", "
                }
            }
            binding.fragmentMovieDetailGenres.text =
                genres
        }
        //description
        binding.fragmentMovieDetailOverview.text = selectedMovie.overview
        //original language
        binding.fragmentMovieDetailOriginLang.text =
            "Original language: ${selectedMovie.original_language}"
        //release date
        binding.fragmentMovieDetailReleased.text = "Release date: ${selectedMovie.release_date}"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

}
