package com.abhriyaroy.nasaapod.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.abhriyaroy.nasaapod.data.PodDataRepository
import com.abhriyaroy.nasaapod.data.entity.PodEntity
import com.abhriyaroy.nasaapod.factory.PodEntityFactory
import com.abhriyaroy.nasaapod.util.ResourceResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.UUID.randomUUID

@RunWith(MockitoJUnitRunner::class)
class PodViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    lateinit var podDataRepository: PodDataRepository

    @Mock
    private lateinit var mockedObserver: Observer<ResourceResult<PodEntity>>
    private lateinit var podViewModel: PodViewModel

    @Before
    fun setup() {
        podViewModel = PodViewModel(podDataRepository)

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun `should return image type pod on getPod call success with no date passed`() {
        runBlocking {
            val resultPod = PodEntityFactory.getImagePodEntity()
            val resultLiveData =
                MutableLiveData<ResourceResult<PodEntity>>(ResourceResult.success(resultPod))
            `when`(podDataRepository.getPod("")).thenReturn(resultLiveData)
            podViewModel.podResultData.observeForever(mockedObserver)

            podViewModel.getPod()

            val inOrder = inOrder(mockedObserver)
            inOrder.verify(mockedObserver).onChanged(ResourceResult.loading())
            inOrder.verify(mockedObserver).onChanged(ResourceResult.success(resultPod))
            verify(podDataRepository).getPod("")
        }
    }

    @Test
    fun `should return video type pod on getPod call success with no date passed`() {
        runBlocking {
            val resultPod = PodEntityFactory.getVideoPodEntity()
            val resultLiveData =
                MutableLiveData<ResourceResult<PodEntity>>(ResourceResult.success(resultPod))
            `when`(podDataRepository.getPod("")).thenReturn(resultLiveData)
            podViewModel.podResultData.observeForever(mockedObserver)

            podViewModel.getPod()

            val inOrder = inOrder(mockedObserver)
            inOrder.verify(mockedObserver).onChanged(ResourceResult.loading())
            inOrder.verify(mockedObserver).onChanged(ResourceResult.success(resultPod))
            verify(podDataRepository).getPod("")
        }
    }

    @Test
    fun `should return image type pod on getPod call success with date passed`() {
        runBlocking {
            val resultPod = PodEntityFactory.getImagePodEntity()
            val resultLiveData =
                MutableLiveData<ResourceResult<PodEntity>>(ResourceResult.success(resultPod))
            val date = randomUUID().toString()
            `when`(podDataRepository.getPod(date)).thenReturn(resultLiveData)
            podViewModel.podResultData.observeForever(mockedObserver)

            podViewModel.getPod(date)

            val inOrder = inOrder(mockedObserver)
            inOrder.verify(mockedObserver).onChanged(ResourceResult.loading())
            inOrder.verify(mockedObserver).onChanged(ResourceResult.success(resultPod))
            verify(podDataRepository).getPod(date)
        }
    }

    @Test
    fun `should return video type pod on getPod call success with date passed`() {
        runBlocking {
            val resultPod = PodEntityFactory.getVideoPodEntity()
            val resultLiveData =
                MutableLiveData<ResourceResult<PodEntity>>(ResourceResult.success(resultPod))
            val date = randomUUID().toString()
            `when`(podDataRepository.getPod(date)).thenReturn(resultLiveData)
            podViewModel.podResultData.observeForever(mockedObserver)

            podViewModel.getPod(date)

            val inOrder = inOrder(mockedObserver)
            inOrder.verify(mockedObserver).onChanged(ResourceResult.loading())
            inOrder.verify(mockedObserver).onChanged(ResourceResult.success(resultPod))
            verify(podDataRepository).getPod(date)
        }
    }

    @Test
    fun `should return error status on getPod call failure with no date passed`() {
        runBlocking {
            val exception = Exception()
            val resultLiveData =
                MutableLiveData<ResourceResult<PodEntity>>(ResourceResult.error(exception))
            `when`(podDataRepository.getPod("")).thenReturn(resultLiveData)
            podViewModel.podResultData.observeForever(mockedObserver)

            podViewModel.getPod()

            val inOrder = inOrder(mockedObserver)
            inOrder.verify(mockedObserver).onChanged(ResourceResult.loading())
            inOrder.verify(mockedObserver).onChanged(ResourceResult.error(exception))
            verify(podDataRepository).getPod("")
        }
    }

    @Test
    fun `should return error status on getPod call failure with date passed`() {
        runBlocking {
            val exception = Exception()
            val resultLiveData =
                MutableLiveData<ResourceResult<PodEntity>>(ResourceResult.error(exception))
            val date = randomUUID().toString()
            `when`(podDataRepository.getPod(date)).thenReturn(resultLiveData)
            podViewModel.podResultData.observeForever(mockedObserver)

            podViewModel.getPod(date)

            val inOrder = inOrder(mockedObserver)
            inOrder.verify(mockedObserver).onChanged(ResourceResult.loading())
            inOrder.verify(mockedObserver).onChanged(ResourceResult.error(exception))
            verify(podDataRepository).getPod(date)
        }
    }

    @After
    fun tearDown() {
        verifyNoMoreInteractions(podDataRepository, mockedObserver)
    }
}
