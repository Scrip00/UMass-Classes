package com.scrip0.umassclasses.api

import com.scrip0.umassclasses.api.entities.UMassResponse
import com.scrip0.umassmaps.other.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UMassAPI {

    @GET("courses")
    suspend fun getCourses(
    ): Response<UMassResponse>

}