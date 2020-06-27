package com.abhriyaroy.nasaapod.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhriyaroy.nasaapod.data.PodDataRepository
import com.abhriyaroy.nasaapod.data.entity.PodEntity
import com.abhriyaroy.nasaapod.util.ResourceResult
import kotlinx.coroutines.launch

class PodViewModel(private val podDataRepository: PodDataRepository) : ViewModel() {

    private var podLiveData = MutableLiveData<ResourceResult<PodEntity>>()
    val podResultData: LiveData<ResourceResult<PodEntity>>
        get() = podLiveData

    fun getPod(date: String = "") {
        podLiveData.value = ResourceResult.loading()
        viewModelScope.launch {
            podDataRepository.getPod(date).let {
                podLiveData.value = it.value
            }
        }
    }

}
