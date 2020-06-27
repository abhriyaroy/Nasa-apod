package com.abhriyaroy.nasaapod.di

import com.abhriyaroy.nasaapod.data.PodDataRepository
import com.abhriyaroy.nasaapod.data.PodDataRepositoryImpl
import com.abhriyaroy.nasaapod.data.datasource.remote.PodRemoteDataSource
import com.abhriyaroy.nasaapod.data.datasource.remote.PodRemoteDataSourceImpl
import com.abhriyaroy.nasaapod.data.datasource.remote.PodService
import com.abhriyaroy.nasaapod.data.datasource.remote.config.NASA_BASE_URL
import com.abhriyaroy.nasaapod.util.DateUtil
import com.abhriyaroy.nasaapod.util.Serializer
import com.abhriyaroy.nasaapod.viewmodel.PodViewModel
import com.google.gson.GsonBuilder
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule: Module = module {
    single<PodRemoteDataSource> { PodRemoteDataSourceImpl(get()) }
    single<PodDataRepository> { PodDataRepositoryImpl(get(), get()) }
}

val networkModule: Module = module {
    single<Retrofit> {
        Retrofit.Builder()
            .baseUrl(NASA_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    single<PodService> {
        providePodService(get())
    }
}

val viewModelModule: Module = module {
    viewModel { PodViewModel(get()) }
}

val uiModule: Module = module {
}

val utilModule: Module = module {
    single<Serializer>()
    single<DateUtil>()
}


private fun providePodService(retrofit: Retrofit): PodService =
    retrofit.create(PodService::class.java)

