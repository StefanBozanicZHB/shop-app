package com.zhbcompany.todo.feature_todo.data.di

import android.content.Context
import androidx.room.Room
import com.zhbcompany.todo.feature_todo.data.local.TodoDao
import com.zhbcompany.todo.feature_todo.data.local.TodoDatabase
import com.zhbcompany.todo.feature_todo.data.remote.TodoApi
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideDatabase(context: Context): TodoDatabase {
    return Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        TodoDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

fun provideTodoDao(database: TodoDatabase): TodoDao {
    return database.dao
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .baseUrl("https://todo-3bdd7-default-rtdb.firebaseio.com/")
        .build()
}

fun provideRetrofitApi(retrofit: Retrofit): TodoApi {
    return retrofit.create(TodoApi::class.java)
}

val dataModule: Module = module {
    single { provideDatabase(androidContext()) }
    single { provideTodoDao(get()) }
    single { provideRetrofit() }
    single { provideRetrofitApi(get()) }
}
