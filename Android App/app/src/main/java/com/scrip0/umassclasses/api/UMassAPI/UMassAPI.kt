package com.scrip0.umassclasses.api.UMassAPI

import com.scrip0.umassclasses.api.TextAPI.entities.TextResponse
import com.scrip0.umassclasses.api.UMassAPI.entities.UMassResponse
import com.scrip0.umassmaps.other.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface UMassAPI {

    @GET("courses")
    suspend fun getCourses(
    ): Response<UMassResponse>

     @GET("courses")
    suspend fun getCoursesFromPage(
        @Query("page")
        text1: String
    ): Response<UMassResponse>

}