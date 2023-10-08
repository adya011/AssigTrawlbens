package com.nanda.assigtrawlbens

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.java.KoinAndroidApplication
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setupKoin()
    }

    private fun setupKoin() {
        val koinApp = KoinAndroidApplication.create(this, Level.NONE)
            .androidContext(this)

        startKoin(koinApp)
    }
}