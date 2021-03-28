package com.appetiser.challenge.data.services

import com.appetiser.challenge.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Jp Cabrera on 3/26/2021.
 */
class RetrofitBuilder {

    val builder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.ENDPOINT)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val apiService: ChallengeService by lazy {
        builder
            .build()
            .create(ChallengeService::class.java)
    }
}