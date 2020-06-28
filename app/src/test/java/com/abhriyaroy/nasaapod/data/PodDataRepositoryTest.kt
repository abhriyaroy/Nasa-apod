package com.abhriyaroy.nasaapod.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.abhriyaroy.nasaapod.data.datasource.remote.PodRemoteDataSource
import com.abhriyaroy.nasaapod.exception.NoInternetException
import com.abhriyaroy.nasaapod.exception.PodFetchException
import com.abhriyaroy.nasaapod.factory.PodEntityFactory
import com.abhriyaroy.nasaapod.util.DateUtil
import com.abhriyaroy.nasaapod.util.ResourceResult
import com.abhriyaroy.nasaapod.util.Status
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import java.net.UnknownHostException
import java.util.UUID.randomUUID


@RunWith(MockitoJUnitRunner::class)
class PodDataRepositoryTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var podRemoteDataSource: PodRemoteDataSource

    @Mock
    private lateinit var dateUtil: DateUtil
    private lateinit var podDateRepository: PodDataRepository

    @Before
    fun setup() {
        podDateRepository = PodDataRepositoryImpl(podRemoteDataSource, dateUtil)
    }

    @Test
    fun `should return image type pod entity of passed date wrapped in success result type on getPod call success`() {
        runBlocking {
            val date = randomUUID().toString()
            val resultPod = PodEntityFactory.getImagePodEntity()
            `when`(podRemoteDataSource.getPod(date)).thenReturn(Response.success(resultPod))

            val obtainedResult = podDateRepository.getPod(date)

            assertTrue(obtainedResult.value!!.status == Status.SUCCESS)
            assertEquals(ResourceResult.success(resultPod), obtainedResult.value)
            verify(podRemoteDataSource).getPod(date)
        }
    }

    @Test
    fun `should return movie type pod entity of passed date wrapped in success result type on getPod call success`() {
        runBlocking {
            val date = randomUUID().toString()
            val resultPod = PodEntityFactory.getVideoPodEntity()
            `when`(podRemoteDataSource.getPod(date)).thenReturn(Response.success(resultPod))

            val obtainedResult = podDateRepository.getPod(date)

            assertTrue(obtainedResult.value!!.status == Status.SUCCESS)
            assertEquals(ResourceResult.success(resultPod), obtainedResult.value)
            verify(podRemoteDataSource).getPod(date)
        }
    }

    @Test
    fun `should return image type pod entity of current date wrapped in success result type on getPod call success when date is not passed`() {
        runBlocking {
            val date = randomUUID().toString()
            val resultPod = PodEntityFactory.getImagePodEntity()
            `when`(dateUtil.getTodayDate()).thenReturn(date)
            `when`(podRemoteDataSource.getPod(date)).thenReturn(Response.success(resultPod))

            val obtainedResult = podDateRepository.getPod("")

            assertTrue(obtainedResult.value!!.status == Status.SUCCESS)
            assertEquals(ResourceResult.success(resultPod), obtainedResult.value)
            verify(podRemoteDataSource).getPod(date)
            verify(dateUtil).getTodayDate()
        }
    }

    @Test
    fun `should return movie type pod entity of current date wrapped in success result type on getPod call success date is not passed`() {
        runBlocking {
            val date = randomUUID().toString()
            val resultPod = PodEntityFactory.getVideoPodEntity()
            `when`(dateUtil.getTodayDate()).thenReturn(date)
            `when`(podRemoteDataSource.getPod(date)).thenReturn(Response.success(resultPod))

            val obtainedResult = podDateRepository.getPod("")

            assertTrue(obtainedResult.value!!.status == Status.SUCCESS)
            assertEquals(ResourceResult.success(resultPod), obtainedResult.value)
            verify(podRemoteDataSource).getPod(date)
            verify(dateUtil).getTodayDate()
        }
    }

    @Test
    fun `should return pod fetch exception when response is not successful on getPod call failure with passed date`() {
        runBlocking {
            val date = randomUUID().toString()
            `when`(podRemoteDataSource.getPod(date)).thenReturn(
                Response.error(
                    400, ResponseBody.create(
                        null,
                        randomUUID().toString()
                    )
                )
            )

            val result = podDateRepository.getPod(date)

            assertTrue(result.value!!.status == Status.ERROR)
            assertTrue(result.value!!.error is PodFetchException)
            verify(podRemoteDataSource).getPod(date)
        }
    }

    @Test
    fun `should return pod fetch exception when response is not successful on getPod call failure with no date passed`() {
        runBlocking {
            val date = randomUUID().toString()
            `when`(podRemoteDataSource.getPod(date)).thenReturn(
                Response.error(
                    400, ResponseBody.create(
                        null,
                        randomUUID().toString()
                    )
                )
            )
            `when`(dateUtil.getTodayDate()).thenReturn(date)

            val result = podDateRepository.getPod("")

            assertTrue(result.value!!.status == Status.ERROR)
            assertTrue(result.value!!.error is PodFetchException)
            verify(podRemoteDataSource).getPod(date)
            verify(dateUtil).getTodayDate()
        }
    }

    @Test
    fun `should return no internet exception on getPod call failure with date passed`() {
        runBlocking {
            val date = randomUUID().toString()
            given(podRemoteDataSource.getPod(date)).willThrow(UnknownHostException())

            val result = podDateRepository.getPod(date)

            assertTrue(result.value!!.status == Status.ERROR)
            assertTrue(result.value!!.error is NoInternetException)
            verify(podRemoteDataSource).getPod(date)
        }
    }

    @Test
    fun `should return no internet exception on getPod call failure with no date passed`() {
        runBlocking {
            val date = randomUUID().toString()
            given(podRemoteDataSource.getPod(date)).willThrow(UnknownHostException())
            `when`(dateUtil.getTodayDate()).thenReturn(date)

            val result = podDateRepository.getPod("")

            assertTrue(result.value!!.status == Status.ERROR)
            assertTrue(result.value!!.error is NoInternetException)
            verify(podRemoteDataSource).getPod(date)
            verify(dateUtil).getTodayDate()
        }
    }

    @After
    fun tearDown() {
        Mockito.verifyNoMoreInteractions(
            podRemoteDataSource,
            dateUtil
        )
    }

}
