package com.zhbcompany.todo

import android.app.Application
import com.zhbcompany.todo.di.appModule
import com.zhbcompany.todo.di.dataModule
import com.zhbcompany.todo.di.domainModule
import com.zhbcompany.todo.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ShopApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoinInApp()
    }

    private fun startKoinInApp() {
        startKoin {
            androidContext(this@ShopApp)
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
}