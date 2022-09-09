package com.diana.sample

import android.app.Application
import com.diana.appcore.repository.UserRepository
import com.diana.appcore.sources.NetworkSources
import com.diana.appcore.sources.WebServices
import com.diana.sample.ui.MainViewModel
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                WebServices.inject(),
                NetworkSources.inject(),
                UserRepository.inject(),
                MainViewModel.inject()
            )
        }
    }
}