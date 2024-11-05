package com.zhbcompany.todo.feature_todo.data.di

import android.content.Context
import androidx.room.Room
import com.zhbcompany.todo.feature_todo.data.local.TodoDao
import com.zhbcompany.todo.feature_todo.data.local.TodoDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

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

val dataModule: Module = module {
    single { provideDatabase(androidContext()) }
    single { provideTodoDao(get()) }
}
