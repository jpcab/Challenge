package com.appetiser.challenge.views

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.appetiser.challenge.R
import com.appetiser.challenge.models.Movie
import com.appetiser.challenge.utils.Status
import com.appetiser.challenge.viewmodels.MovieViewModel
import com.appetiser.challenge.views.adapters.MovieAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

/**
 * Main Class contains the list of tracks
 * Implements the [MovieAdapter.OnItemClickListener] and extends [BaseActivity]
 */
class MainActivity : BaseActivity(), MovieAdapter.OnItemClickListener {

    companion object {
        val TRACK_KEY = "track"
    }

    private lateinit var movieViewModel: MovieViewModel
    private lateinit var movieAdapter: MovieAdapter
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        movieViewModel =
            ViewModelProvider(
                this,
                ViewModelProvider.AndroidViewModelFactory(this.application)
            ).get(
                MovieViewModel::class.java
            )

        provideListStatus()
        provideListData()
        checkLastVisitedActivity()
    }

    /**
     * Checks the cache and determines the last visited activity
     */
    private fun checkLastVisitedActivity() {
        if (cachedData.lastVisitedActivity != MainActivity::class.simpleName) {
            var visitedId = cachedData.lastVisitedId

            val intent = Intent(this, MovieActivity::class.java)
            intent.putExtra(TRACK_KEY, visitedId)
            startActivity(intent)
        }
    }

    /**
     * Provides the data for the list
     */
    private fun provideListData() {
        movieViewModel.getMovies()
        movieViewModel.movies.observe(this, Observer {
            movieAdapter.submitList(it)
        })
    }

    /**
     * Provides the status for the list and updated UI accordingly
     */
    private fun provideListStatus() {
        movieAdapter = MovieAdapter(this, this)
        rv_movies.adapter = movieAdapter

        movieViewModel.moviesStatus.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    rv_movies.visibility = VISIBLE

                    error_icon.visibility = GONE
                    error_text.visibility = GONE

                    progress_circular.visibility = GONE
                }
                Status.ERROR -> {
                    rv_movies.visibility = GONE

                    error_icon.visibility = VISIBLE
                    error_text.visibility = VISIBLE

                    progress_circular.visibility = GONE
                }
                Status.LOADING -> {
                    rv_movies.visibility = GONE

                    error_icon.visibility = GONE
                    error_text.visibility = GONE

                    progress_circular.visibility = VISIBLE
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_item -> {
                movieViewModel.deleteAndFetchNewData()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onItemClick(movie: Movie) {
        val intent = Intent(this, MovieActivity::class.java)
        intent.putExtra(TRACK_KEY, movie.id)
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        movieAdapter.notifyDataSetChanged()
    }
}
