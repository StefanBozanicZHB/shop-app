package com.zhbcompany.shop

import android.app.Application
import com.zhbcompany.shop.di.appModule
import com.zhbcompany.shop.di.dataModule
import com.zhbcompany.shop.di.domainModule
import com.zhbcompany.shop.di.presentationModule
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