package com.zhbcompany.shop.di

import android.content.Context
import androidx.room.Room
import com.zhbcompany.shop.data.local.ShopDao
import com.zhbcompany.shop.data.local.ShopDatabase
import com.zhbcompany.shop.data.remote.ShopApi
import com.zhbcompany.shop.data.repo.ShopRepositoryImpl
import com.zhbcompany.shop.domain.repo.ShopRepository
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun provideDatabase(context: Context): ShopDatabase {
    return Room.databaseBuilder(
        context,
        ShopDatabase::class.java,
        ShopDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

private fun provideShopDao(database: ShopDatabase): ShopDao {
    return database.dao
}

private fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://todo-3bdd7-default-rtdb.firebaseio.com/")
        .build()
}

private fun provideRetrofitApi(retrofit: Retrofit): ShopApi {
    return retrofit.create(ShopApi::class.java)
}

private fun provideShopRepo(db: ShopDatabase, api: ShopApi, dispatcher: CoroutineDispatcher): ShopRepository {
    return ShopRepositoryImpl(db.dao, api, dispatcher)
}

val dataModule: Module = module {
    single { provideDatabase(androidContext()) }
    single { provideShopDao(get()) }
    single { provideRetrofit() }
    single { provideRetrofitApi(get()) }
    single { provideShopRepo(get(), get(), get(named("IoDispatcher"))) }
}
