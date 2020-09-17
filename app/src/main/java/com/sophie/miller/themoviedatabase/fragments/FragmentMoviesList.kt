package com.sophie.miller.themoviedatabase.fragments

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sophie.miller.themoviedatabase.MainActivity

import com.sophie.miller.themoviedatabase.R
import com.sophie.miller.themoviedatabase.adapters.AdapterMoviesList
import com.sophie.miller.themoviedatabase.databinding.FragmentMoviesListBinding
import com.sophie.miller.themoviedatabase.dialogs.ChooseDatesDialog
import com.sophie.miller.themoviedatabase.utils.Logger
import com.sophie.miller.themoviedatabase.viewmodel.MainViewModel

class FragmentMoviesList : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var activity: MainActivity
    private lateinit var binding: FragmentMoviesListBinding

    //util
    private lateinit var moviesAdapter: AdapterMoviesList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentMoviesList()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMoviesListBinding.bind(view)
        //view model
        mainViewModel = ViewModelProvider(activity).get(MainViewModel::class.java)
        //set layout manager to recyclerview
        binding.activityMainRecView.setLayoutManager(
            GridLayoutManager(
                context,
                getNumberOfColumns()
            )
        )
        moviesAdapter = AdapterMoviesList(activity, mainViewModel)
        binding.activityMainRecView.setAdapter(moviesAdapter)


        mainViewModel.moviesList?.observe(activity, Observer { list ->
            mainViewModel.moviesDetails.clear()
            activity.movies.clear()
            Logger.testLog("size: "+list.moviesList.size.toString())
            list.moviesList.forEach {
                //add movie id to a list to display if it is not an adult film
                if (it.adult != null && it.adult == false) {
                    activity.movies.add(it.id.toString())
                }
            }
            moviesAdapter.setMovies(activity.movies)
        })

        binding.activityMainFab.setOnClickListener {
           ChooseDatesDialog(activity).show()
            // mainViewModel.editFilter("2018-4-4", "2018-4-4")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = context as MainActivity
    }

    /**
     * set number of columns based on screen size
     *
     * @return number of columns
     */
    private fun getNumberOfColumns(): Int {
        val columns = 1
        // make a grid with a certain number of columns
        val metrics = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(metrics)
        val width = metrics.widthPixels
        if (width > 3000) {
            return 7
        } else if (width > 2200) {
            return 6
        } else if (width > 1857) {
            return 4
        } else if (width > 1200) {
            return 3
        } else if (width > 600) {
            return 2
        }
        return columns
    }
}
