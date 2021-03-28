package com.appetiser.challenge.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appetiser.challenge.data.dao.MovieDao
import com.appetiser.challenge.models.Movie
import com.appetiser.challenge.models.MovieResult
import com.appetiser.challenge.data.services.RetrofitBuilder
import com.appetiser.challenge.utils.Resource
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.coroutineContext

/**
 * Created by Jp Cabrera on 3/26/2021.
 *
 * This class serves as repository for the Tracks.
 *
 * @param movieDao  used to access movie objects from the database
 * @param retrofitBuilder provides builder to access API data
 *
 */
class MovieRepository(
    val movieDao: MovieDao,
    private val retrofitBuilder: RetrofitBuilder
) {
    private var status: MutableLiveData<Resource<List<Movie>>> = MutableLiveData()
    private var movies: LiveData<List<Movie>> = movieDao.getMovies()

    fun getMoviesStatus(): MutableLiveData<Resource<List<Movie>>> = status
    fun getMoviesLiveData(): LiveData<List<Movie>> = movies

    /**
     * Updates [status] depends on the data flow  @see com.appetiser.challenge.utils.Status
     * Checks if data is available to show , if the list list is empty it will call [getAPIMovies] else it will update the [status]
     */
    suspend fun getMovies() = withContext(Dispatchers.IO) {
        status.postValue(Resource.loading(null))

        if (movieDao.getCount() == 0) {
            getAPIMovies(status)
        } else {
            status.postValue(Resource.success(null))
        }
    }

    /**
     * @param status to update the status of fetching data
     *
     * After successful fetching data it will call [insertMovies] to save the list to the Database
     * Updates the [status] accordingly
     */
    fun getAPIMovies(status: MutableLiveData<Resource<List<Movie>>>) =
        retrofitBuilder.apiService.getList()
            .enqueue(object : Callback<MovieResult> {
                override fun onFailure(call: Call<MovieResult>?, t: Throwable?) {
                    status.postValue(Resource.error("Error", null))
                }

                override fun onResponse(
                    call: Call<MovieResult>?,
                    response: Response<MovieResult>?
                ) {
                    if (response != null) {
                        GlobalScope.launch {
                            insertMovies(response.body().results.sortedBy { r -> r.trackName })
                            status.postValue(
                                Resource.success(
                                    null
                                )
                            )
                        }
                        return
                    }
                    status.postValue(Resource.error("Error", null))
                }
            })

    /**
     * Inserts a list of items into the Database
     *
     * @param [movies] list of [Movie] containing the data
     */
    suspend fun insertMovies(movies: List<Movie>) {
        movies.forEach {
            movieDao.insert(it)
        }
    }

    /**
     * Deletes all data from the database
     */
    suspend fun deleteAllMovie() = withContext(Dispatchers.IO) {
        movieDao.deleteAll()
    }

    /**
     * Retrieves item from the Database based on the provided [id]
     *
     * @param [id] contains the id of the [Movie] to be retrieved
     */
    fun getMovie(id: Int): LiveData<Movie> {
        return movieDao.getMovie(id)
    }

    /**
     * Updates item
     *
     * @param [movie] contains the model to be updated
     */
    suspend fun updateMovie(movie: Movie) = withContext(Dispatchers.IO) {
        movieDao.update(movie)
    }
}

