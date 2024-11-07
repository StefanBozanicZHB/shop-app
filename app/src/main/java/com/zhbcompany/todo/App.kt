package com.zhbcompany.todo

import android.app.Application
import com.zhbcompany.todo.feature_todo.data.di.dataModule
import com.zhbcompany.todo.feature_todo.domain.di.domainModule
import com.zhbcompany.todo.feature_todo.presentation.di.presentationModule
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.module

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoinInApp()
    }

    private fun startKoinInApp() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    appModule,
                    dataModule,
                    domainModule,
                    presentationModule,
                )
            )
        }
    }

    private val appModule: Module = module {
        single { Dispatchers.IO }
    }
}