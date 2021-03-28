package com.appetiser.challenge.data.services

import com.appetiser.challenge.models.MovieResult
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Jp Cabrera on 3/26/2021.
 */
interface ChallengeService {
    @GET("search?term=star&amp;country=au&amp;media=movie&amp;all")
    fun getList(): Call<MovieResult>
}