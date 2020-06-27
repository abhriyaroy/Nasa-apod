package com.abhriyaroy.nasaapod.data.datasource.remote

import com.abhriyaroy.nasaapod.data.entity.PodEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

private const val PLANETARY_POD = "planetary/apod"

interface PodService {
    @GET("${PLANETARY_POD}/")
    suspend fun getPod(
        @Query("api_key") apiKey: String,
        @Query("date") date: String
    ): Response<PodEntity>
}
