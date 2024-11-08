package com.zhbcompany.todo.feature_todo.presentation.di

import com.zhbcompany.todo.feature_todo.domain.use_case.TodoUseCases
import com.zhbcompany.todo.feature_todo.presentation.todo_list.TodoListViewModel
import com.zhbcompany.todo.feature_todo.presentation.todo_new_update.TodoNewUpdateViewModel
import kotlinx.coroutines.CoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.core.qualifier.named
import org.koin.dsl.module

private fun provideTodoListViewModel(
    uceCase: TodoUseCases,
    dispatcher: CoroutineDispatcher
): TodoListViewModel {
    return TodoListViewModel(uceCase, dispatcher)
}

private fun provideTodoNewUpdateViewModel(
    uceCase: TodoUseCases,
    dispatcher: CoroutineDispatcher
): TodoNewUpdateViewModel {
    return TodoNewUpdateViewModel(uceCase, dispatcher)
}

val presentationModule: Module = module {
    viewModel { provideTodoListViewModel(get(), get(named("IoDispatcher"))) }
    viewModel { provideTodoNewUpdateViewModel(get(), get(named("IoDispatcher"))) }
}