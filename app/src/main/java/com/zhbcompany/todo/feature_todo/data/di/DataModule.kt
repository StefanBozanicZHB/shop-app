package com.zhbcompany.todo.feature_todo.data.di

import android.content.Context
import androidx.room.Room
import com.zhbcompany.todo.feature_todo.data.local.TodoDao
import com.zhbcompany.todo.feature_todo.data.local.TodoDatabase
import com.zhbcompany.todo.feature_todo.data.remote.TodoApi
import com.zhbcompany.todo.feature_todo.data.repo.TodoListRepoImpl
import com.zhbcompany.todo.feature_todo.domain.repo.TodoListRepo
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private fun provideDatabase(context: Context): TodoDatabase {
    return Room.databaseBuilder(
        context,
        TodoDatabase::class.java,
        TodoDatabase.DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
}

private fun provideTodoDao(database: TodoDatabase): TodoDao {
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

private fun provideRetrofitApi(retrofit: Retrofit): TodoApi {
    return retrofit.create(TodoApi::class.java)
}

private fun provideTodoRepo(db: TodoDatabase, api: TodoApi, dispatcher: CoroutineDispatcher): TodoListRepo {
    return TodoListRepoImpl(db.dao, api, dispatcher)
}

val dataModule: Module = module {
    single { provideDatabase(androidContext()) }
    single { provideTodoDao(get()) }
    single { provideRetrofit() }
    single { provideRetrofitApi(get()) }
    single { provideTodoRepo(get(), get(), get()) }
}
