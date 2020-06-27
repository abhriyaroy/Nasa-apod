package com.abhriyaroy.nasaapod

import android.app.Application
import com.abhriyaroy.nasaapod.di.dataModule
import com.abhriyaroy.nasaapod.di.networkModule
import com.abhriyaroy.nasaapod.di.utilModule
import com.abhriyaroy.nasaapod.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class NasaApodApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val moduleList = listOf(dataModule, networkModule, viewModelModule, utilModule)
        startKoin {
            androidContext(this@NasaApodApplication)
            modules(moduleList)
        }
    }
}
