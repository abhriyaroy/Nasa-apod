package com.abhriyaroy.nasaapod.data.datasource.remote

import com.abhriyaroy.nasaapod.data.datasource.remote.config.API_KEY
import com.abhriyaroy.nasaapod.data.entity.PodEntity
import retrofit2.Response

interface PodRemoteDataSource {
    suspend fun getPod(date: String): Response<PodEntity>
}

class PodRemoteDataSourceImpl(private val podService: PodService) :
    PodRemoteDataSource {

    override suspend fun getPod(date: String): Response<PodEntity> {
        return podService.getPod(API_KEY, date)
    }
}
