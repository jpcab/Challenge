package com.appetiser.challenge.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.appetiser.challenge.data.dao.MovieDao
import com.appetiser.challenge.models.Movie

/**
 * Created by Jp Cabrera on 3/26/2021.
 *
 * Initializing Database singleton using Room database
 * Used DAO to access objects
 *
 */
@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {

        @Volatile
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(context: Context): MovieDatabase {
            val instance = INSTANCE

            if (instance != null) {
                return instance
            }

            synchronized(this) {
                val databaseInstance = Room.databaseBuilder(
                    context.applicationContext,
                    MovieDatabase::class.java,
                    "movie_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()

                INSTANCE = databaseInstance

                return databaseInstance
            }
        }
    }
}