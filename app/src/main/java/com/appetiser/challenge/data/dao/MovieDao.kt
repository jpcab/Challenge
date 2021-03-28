package com.appetiser.challenge.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.appetiser.challenge.models.Movie


/**
 * Created by Jp Cabrera on 3/26/2021.
 */
@Dao
interface MovieDao : BaseDao<Movie> {

    @Query("Select * from movie_table")
    fun getMovies(): LiveData<List<Movie>>

    @Query("Select * from movie_table WHERE id = :id")
    fun getMovie(id: Int): LiveData<Movie>

    @Query("DELETE FROM movie_table")
    fun deleteAll()

    @Query("SELECT COUNT(*) FROM movie_table")
    suspend fun getCount(): Int
}