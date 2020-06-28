package com.abhriyaroy.nasaapod.data.datasource.remote

import com.abhriyaroy.nasaapod.data.datasource.remote.config.API_KEY
import com.abhriyaroy.nasaapod.data.entity.PodEntity
import retrofit2.Response
import java.net.UnknownHostException

interface PodRemoteDataSource {
    @Throws(UnknownHostException::class)
    suspend fun getPod(date: String): Response<PodEntity>
}

class PodRemoteDataSourceImpl(private val podService: PodService) :
    PodRemoteDataSource {

    @Throws(UnknownHostException::class)
    override suspend fun getPod(date: String): Response<PodEntity> {
        return podService.getPod(API_KEY, date)
    }
}
