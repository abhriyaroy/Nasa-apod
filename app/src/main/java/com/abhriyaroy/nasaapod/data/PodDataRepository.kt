package com.abhriyaroy.nasaapod.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abhriyaroy.nasaapod.data.datasource.remote.PodRemoteDataSource
import com.abhriyaroy.nasaapod.data.entity.PodEntity
import com.abhriyaroy.nasaapod.exception.NoInternetException
import com.abhriyaroy.nasaapod.exception.PodFetchException
import com.abhriyaroy.nasaapod.util.DateUtil
import com.abhriyaroy.nasaapod.util.ResourceResult
import java.net.UnknownHostException


interface PodDataRepository {
    suspend fun getPod(date: String = ""): LiveData<ResourceResult<PodEntity>>
}

class PodDataRepositoryImpl(
    private val podRemoteDataSource: PodRemoteDataSource,
    private val dateUtil: DateUtil
) : PodDataRepository {

    override suspend fun getPod(date: String): LiveData<ResourceResult<PodEntity>> {
        val resolvedDate = getResolvedDate()
        return MutableLiveData<ResourceResult<PodEntity>>().let { mutableLiveData ->
            try {
                with(podRemoteDataSource.getPod(resolvedDate)) {
                    if (isSuccessful) {
                        mutableLiveData.value = ResourceResult.success(body()!!)
                    } else {
                        mutableLiveData.value =
                            ResourceResult.error(PodFetchException())
                    }
                }
            } catch (e: UnknownHostException) {
                mutableLiveData.value = ResourceResult.error(NoInternetException())
            }
            mutableLiveData
        }
    }

    private fun getResolvedDate(date: String = ""): String {
        return if (date.isEmpty()) {
            dateUtil.getTodayDate()
        } else {
            date
        }
    }
}
