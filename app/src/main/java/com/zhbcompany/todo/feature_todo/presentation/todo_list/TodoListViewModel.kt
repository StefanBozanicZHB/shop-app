package com.zhbcompany.todo.feature_todo.presentation.todo_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zhbcompany.todo.feature_todo.domain.model.TodoItem
import com.zhbcompany.todo.feature_todo.domain.use_case.TodoUseCaseResult
import com.zhbcompany.todo.feature_todo.domain.use_case.TodoUseCases
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TodoListViewModel(
    private val todoUseCases: TodoUseCases,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

//    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    // todo use state or flow
    private val _state = mutableStateOf(TodoListState())
    val state: State<TodoListState> = _state

    private var undoTodoItem: TodoItem? = null

    private var getTodoItemsJob: Job? = null

    // todo how to use CoroutineExceptionHandler
    private val errorHandler = CoroutineExceptionHandler { _, e ->
        e.printStackTrace()
        _state.value = _state.value.copy(
            error = e.message,
            isLoading = false
        )
    }

    fun onEvent(event: TodoListEvent) {
        when (event) {
            is TodoListEvent.Delete -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    todoUseCases.deleteTodoItem(event.todo)
                    getTodoItems()
                    undoTodoItem = event.todo
                }
            }

            is TodoListEvent.Sort -> {
                val stateOrderAlreadyMatchedEventOrder =
                    _state.value.todoItemOrder::class == event.todoItemOrder::class &&
                            _state.value.todoItemOrder.showArchived == event.todoItemOrder.showArchived &&
                            _state.value.todoItemOrder.sortingDirection == event.todoItemOrder.sortingDirection

                if (stateOrderAlreadyMatchedEventOrder) {
                    return
                }

                _state.value = _state.value.copy(
                    todoItemOrder = event.todoItemOrder
                )

                getTodoItems()
            }

            is TodoListEvent.ToggleArchived -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    todoUseCases.toggleArchiveTodoItem(todo = event.todo)
                    getTodoItems()
                }
            }

            is TodoListEvent.ToggleCompleted -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    todoUseCases.toggleCompleteTodoItem(todo = event.todo)
                    getTodoItems()
                }
            }

            TodoListEvent.UndoDelete -> {
                viewModelScope.launch(dispatcher + errorHandler) {
                    todoUseCases.addTodoItem(undoTodoItem ?: return@launch)
                    undoTodoItem = null
                    getTodoItems()
                }
            }
        }
    }

    fun getTodoItems() {
        getTodoItemsJob?.cancel()

        getTodoItemsJob = viewModelScope.launch(dispatcher + errorHandler) {
            val result = todoUseCases.getTodoItems(
                todoItemOrder = _state.value.todoItemOrder
            )
            when (result) {
                is TodoUseCaseResult.Success -> {
                    _state.value = _state.value.copy(
                        todoItems = result.todoItems,
                        todoItemOrder = _state.value.todoItemOrder,
                        isLoading = false
                    )
                }

                is TodoUseCaseResult.Error -> {
                    _state.value = _state.value.copy(
                        error = "Error: ${result.message}",
                        isLoading = false
                    )
                }
            }
        }
    }
}