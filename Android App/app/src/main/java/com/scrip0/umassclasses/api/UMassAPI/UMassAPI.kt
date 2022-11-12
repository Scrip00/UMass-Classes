package com.scrip0.umassclasses.api.UMassAPI

import com.scrip0.umassclasses.api.UMassAPI.entities.UMassResponse
import retrofit2.Response
import retrofit2.http.GET


interface UMassAPI {

    @GET("courses")
    suspend fun getCourses(
    ): Response<UMassResponse>

}