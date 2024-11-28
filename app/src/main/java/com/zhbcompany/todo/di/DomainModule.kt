package com.zhbcompany.todo.di

import com.zhbcompany.todo.domain.repo.TodoListRepo
import com.zhbcompany.todo.domain.use_case.TodoUseCases
import org.koin.core.module.Module
import org.koin.dsl.module

private fun provideTodoUseCases(repo: TodoListRepo): TodoUseCases {
    return TodoUseCases(repo)
}

val domainModule: Module = module {
    single { provideTodoUseCases(get()) }
}