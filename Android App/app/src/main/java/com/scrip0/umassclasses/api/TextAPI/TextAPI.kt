package com.scrip0.umassclasses.api.TextAPI

import com.scrip0.umassclasses.api.TextAPI.entities.TextResponse
import com.scrip0.umassmaps.other.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface TextAPI {

    @GET("datatxt/sim/v1/")
    suspend fun getSimilarity(
        @Query("text1")
        text1: String,
        @Query("text2")
        text2: String,
        @Query("token")
        token: String = Constants.API_KEY
    ): Response<TextResponse>
}