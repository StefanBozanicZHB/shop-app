package com.zhbcompany.todo

import android.app.Application
import com.zhbcompany.todo.feature_todo.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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
                    dataModule,
                )
            )
        }
    }
}