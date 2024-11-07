package com.zhbcompany.todo.feature_todo.presentation.di

import com.zhbcompany.todo.feature_todo.domain.use_case.TodoUseCases
import com.zhbcompany.todo.feature_todo.presentation.todo_list.TodoListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

private fun provideTodoListViewModel(
    uceCase: TodoUseCases,
    dispatcher: CoroutineDispatcher
): TodoListViewModel {
    return TodoListViewModel(uceCase, dispatcher)
}

val presentationModule: Module = module {
    viewModel { provideTodoListViewModel(get(), get()) }
}