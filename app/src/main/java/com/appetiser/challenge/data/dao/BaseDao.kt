package com.appetiser.challenge.data.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

/**
 * Created by Jp Cabrera on 3/26/2021.
 */

interface BaseDao<T> {
    @Insert
    suspend fun insert(obj: T): Long

    @Update
    fun update(obj: T)

    @Delete
    fun delete(obj: T)
}