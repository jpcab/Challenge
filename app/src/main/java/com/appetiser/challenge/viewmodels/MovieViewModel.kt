package com.appetiser.challenge.viewmodels


import android.app.Application
import android.text.Spanned
import androidx.core.text.HtmlCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.appetiser.challenge.data.MovieDatabase
import com.appetiser.challenge.data.services.RetrofitBuilder
import com.appetiser.challenge.models.Movie
import com.appetiser.challenge.repositories.MovieRepository
import com.appetiser.challenge.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Jp Cabrera on 3/26/2021.
 *
 * A viewmodel to manage data on Tracks
 *
 * @param application to be used on database initialization
 */
class MovieViewModel(application: Application) : AndroidViewModel(application) {

    var moviesStatus = MutableLiveData<Resource<List<Movie>>>()
    var movie = MutableLiveData<Movie>()
    var movies: LiveData<List<Movie>> = MutableLiveData()
    private val movieRepository: MovieRepository

    init {
        var movieDao = MovieDatabase.getInstance(application).movieDao()
        movieRepository = MovieRepository(movieDao, RetrofitBuilder())
        moviesStatus = movieRepository.getMoviesStatus()
        movies = movieRepository.getMoviesLiveData()
    }

    /**
     * Fetches LiveData<Movie>
     *
     * @param id the id of the track to be fetched
     *
     * @return return a observable Movie
     */
    fun getMovie(id: Int): LiveData<Movie> {
        return movieRepository.getMovie(id)
    }

    /**
     * Loads all Tracks
     */
    fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.getMovies()
        }
    }

    /**
     * Converts String into Spanned , checks data availability and return message accordingly
     *
     * @param item data to be converted
     *
     * @return a formatted string to Spanned
     */
    fun HTMLEncode(item: String?): Spanned {
        val message: String = if (item.isNullOrEmpty()) {
            "Description : Not Specified"
        } else {
            item
        }

        return HtmlCompat.fromHtml(
            message, HtmlCompat.FROM_HTML_MODE_COMPACT
        )
    }

    /**
     * Updates tracks with incremented visit count and latest visit Date
     *
     * @param movie contains
     */
    fun updateVisitedTimes(movie: Movie?) {
        if (movie == null) {
            return
        }
        movie.visitCount++
        movie.visitDate = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.updateMovie(movie)
        }
    }

    /**
     * Deletes all data from movies_table  and fetches new set on data from API
     */
    fun deleteAndFetchNewData() {
        viewModelScope.launch(Dispatchers.IO) {
            movieRepository.deleteAllMovie()
            getMovies()
        }

    }
}