package com.sophie.miller.themoviedatabase

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.sophie.miller.themoviedatabase.adapters.AdapterMoviesList
import com.sophie.miller.themoviedatabase.constants.UIconstants.FRAGMENT_DETAIL
import com.sophie.miller.themoviedatabase.constants.UIconstants.FRAGMENT_LIST
import com.sophie.miller.themoviedatabase.databinding.ActivityMainBinding
import com.sophie.miller.themoviedatabase.fragments.FragmentMovieDetail
import com.sophie.miller.themoviedatabase.fragments.FragmentMoviesList
import com.sophie.miller.themoviedatabase.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    //fragments
    private lateinit var moviesListFragment: FragmentMoviesList
    private lateinit var movieDetailFragment: FragmentMovieDetail
    private var currentFRagment = FRAGMENT_LIST
    private val fragmentKey = "fragment"

    //values
    val movies = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        //inititialize fragments
        moviesListFragment = FragmentMoviesList.newInstance()
        movieDetailFragment = FragmentMovieDetail.newInstance()
        //setFragment
        if (savedInstanceState?.getInt(fragmentKey) != null) {
            currentFRagment = savedInstanceState.getInt(fragmentKey)
        }
        setFragment(currentFRagment)
    }

    /**
     * setFragment
     * 1 = movie detail
     * 0 / else = list of movies
     */
    fun setFragment(fragmentId: Int) {
        if (fragmentId == FRAGMENT_DETAIL) {
            currentFRagment = FRAGMENT_DETAIL
            //show back arrow
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.setHomeButtonEnabled(true)
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, movieDetailFragment).commitNow()
        } else {
            currentFRagment = FRAGMENT_LIST
            //show back arrow
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
            supportActionBar?.setHomeButtonEnabled(false)
            supportFragmentManager.beginTransaction()
                .replace(R.id.activity_main_container, moviesListFragment).commitNow()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        setFragment(FRAGMENT_LIST)
        return true
    }

    override fun onBackPressed() {
        setFragment(FRAGMENT_LIST)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(fragmentKey, currentFRagment)
    }

    fun setFiler(startDate: String, endDate: String) {
        mainViewModel.editFilter(startDate, endDate)
    }
}
